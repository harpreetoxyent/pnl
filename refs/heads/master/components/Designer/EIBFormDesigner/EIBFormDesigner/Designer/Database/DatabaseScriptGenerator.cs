using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using System.Windows.Forms;

namespace EIBFormDesigner.Designer.Database
{
    class DatabaseScriptGenerator
    {
        private DataSet currentDataSet = null;        
        public string databaseName = null;
        public string tableName = null;
        public string primaryKeyList = null;
        public string parentId = "";
        public string childId = "";
        bool primaryScriptsAvailable = false;
        string indexKeyStmt = "id_index";
        static int indexKeyCounter = 0;
       
        public string PrimaryKey
        {
            get
            {
                return " ,  INDEX ( " + primaryKeyList + ")";
            }
        }
        public string ForeignKey
        {
            get
            {
                //FOREIGN KEY (parent_id) REFERENCES parent(id)
                return " , foreign key ( " + parentId + " REFERENCES parent (" + childId + ")";
            }
        }
        public string UseDBScript 
        {
            get
            {
                return "use " + databaseName + " ;";
            }
        }
        public string DropDBScript
        {
            get
            {
                return "drop database if exists " + databaseName + " ;";
            }
        }
        public string CreateDBScript
        {
            get
            {
                return "create database " + databaseName + " ;";
            }
        }
        public string CreateTableScript 
        {

            get
            {
                return "create table " + tableName + " ( ";
            }
        }
        public string FrameControl = "Frame";
        public string DatabaseName
        {
            get
            {
                return databaseName;
            }
            set
            {
                databaseName = value;
            }
        }
        public string TableName
        {
            get
            {
                return tableName;
            }
            set
            {
                tableName = value;
            }
        }
        public DataSet CurrentDataSet
        {
            get
            {
                return currentDataSet;
            }
            set
            {
                currentDataSet = value;
            }
        }
        public string GenerateDatabaseScripts(IBaseWindow databaseWindow)
        {
            string databaseScripts = "";
            if (databaseWindow is BaseWindow)
            {
                databaseName = ((BaseWindow)databaseWindow).baseFrame.ControlName;
                bool alterScriptsAvailable = false;
                databaseScripts = databaseScripts + DropDBScript;
                databaseScripts = databaseScripts + CreateDBScript;
                databaseScripts = databaseScripts + UseDBScript;
                foreach (DataTable table in currentDataSet.Tables)
                {
                    tableName = table.TableName;
                    if (tableName.Contains(" "))
                    {
                        MessageBox.Show("Table must not have any blank space.");
                        return "";
                    }
                    primaryKeyList = null;
                    databaseScripts = databaseScripts + CreateTableScript;
                    int counter = 1;
                    string columnString = "";
                    PrimaryKeyList(table);
                    foreach (DataColumn column in table.Columns)
                    {
                        String mySQLType = MySQLDataTypeConverter(column);
                        if (mySQLType.Trim().Equals(DatabaseConstants.CharType))
                        {

                            string charSize = column.Caption;

                            if (!charSize.Trim().Equals(""))
                            {
                                mySQLType = mySQLType + "( " + charSize.ToString() + ") ";
                            }
                        }
                        if (mySQLType.Trim().Equals("VarChar"))
                        {

                            string charSize = column.Caption;

                            if (!charSize.Trim().Equals(""))
                            {
                                mySQLType = mySQLType + "( " + charSize.ToString() + ") ";
                            }
                        }
                        columnString = columnString + column.ColumnName + "  " + mySQLType;
                        if (primaryKeyList != null)
                        {
                            if (!primaryKeyList.Contains(","))
                            {
                                columnString += PrimaryColumn(column);
                            }
                        }
                        columnString += " " + (column.AllowDBNull ? "" : "not null");
                        columnString += " " + AutoIncrementColumn(column);
                        if (counter == table.Columns.Count)
                        {
                            if (primaryKeyList != null && primaryScriptsAvailable)
                            {
                                if (primaryKeyList.Contains(","))
                                {
                                    columnString += ", primary key(" + primaryKeyList + ")";
                                }
                                columnString = columnString + PrimaryKey;
                            }
                            columnString = columnString + " ) TYPE = INNODB ;";
                        }
                        else
                        {
                            columnString = columnString + " , ";
                        }
                        counter++;
                    }
                    databaseScripts = databaseScripts + columnString;
                }
                string constAlterScripts = "alter table ";
                string alterScripts = "";
                string indexScripts = "";
                string constIndexScripts = "create index ";
                foreach (DataRelation relation in currentDataSet.Relations)
                {
                    alterScriptsAvailable = true;
                    DataTable parentTable = relation.ParentTable;
                    DataTable childTable = relation.ChildTable;
                    DataColumn[] parentColumns = relation.ParentColumns;
                    DataColumn[] childColumns = relation.ChildColumns;
                    alterScripts = alterScripts + constAlterScripts + childTable.TableName + " ";
                    alterScripts = alterScripts + " add foreign key ( ";
                    string childColumnList = null;
                    foreach (DataColumn column in childColumns)
                    {
                        if (childColumnList != null)
                        {
                            childColumnList = childColumnList + " , ";
                        }
                        childColumnList = childColumnList + column.ColumnName;
                    }
                    alterScripts = alterScripts + childColumnList + " ) ";
                    indexScripts = indexScripts + constIndexScripts + indexKeyStmt + indexKeyCounter.ToString() + " ON " + childTable.TableName + " (";
                    indexKeyCounter++;
                    alterScripts = alterScripts + " references " + parentTable.TableName + "( ";
                    string parentColumnList = null;
                    foreach (DataColumn column in parentColumns)
                    {
                        if (parentColumnList != null)
                        {
                            parentColumnList = parentColumnList + " , ";
                        }
                        parentColumnList = parentColumnList + column.ColumnName;
                    }
                    indexScripts = indexScripts + childColumnList + " );";
                    alterScripts = alterScripts + parentColumnList + " );";
                }
                if (alterScriptsAvailable)
                {
                    databaseScripts = databaseScripts + indexScripts + alterScripts;
                }
            }
            else
            {
                MessageBox.Show("Database name can not be null");
            }
            return databaseScripts;
        }

        public void PrimaryKeyList(DataTable table)
        {
            foreach (DataColumn column in table.PrimaryKey)
            {
                primaryScriptsAvailable = true;
                if (primaryKeyList != null && !primaryKeyList.Equals(""))
                {
                    primaryKeyList = primaryKeyList + ",";
                }
                primaryKeyList = primaryKeyList + column.ColumnName;
            }
        }
        public string AutoIncrementColumn(DataColumn column)
        {
            if (column.AutoIncrement)
            {
                return "AUTO_INCREMENT";
            }
            return "";
        }
        public string PrimaryColumn(DataColumn column)
        {
            if (column.Unique)
            {
                return " PRIMARY KEY ";
            }
            return "";
        }
        public string MySQLDataTypeConverter(DataColumn dbColumn)
        {
            string netType = dbColumn.DataType.Name;
            if (netType.Trim().Equals((typeof(System.Boolean)).Name))
            {
                return "BOOL";
            }
            else if (netType.Trim().Equals((typeof(System.Byte)).Name))
            {
                return "TINYINT UNSIGNED";
            }
            else if (netType.Trim().Equals((typeof(System.Char)).Name))
            {
                return DatabaseConstants.CharType;
            }
            else if (netType.Trim().Equals((typeof(System.String)).Name))
            {
                bool isVarChar = true;
                try
                {
                    int caption = Int32.Parse(dbColumn.Caption);
                }
                catch
                {
                    isVarChar = false;
                }
                if (isVarChar)
                    return "VarChar";
                else
                    return "LongText";
            }
            else if (netType.Trim().Equals((typeof(System.Int32)).Name))
            {
                return "BIGINT"; 
            }
            else if (netType.Trim().Equals((typeof(System.UInt16)).Name))
            {
                return "SMALLINT UNSIGNED";
            }
            else if (netType.Trim().Equals((typeof(System.UInt32)).Name))
            {
                return "INT UNSIGNED";
            }
            else if (netType.Trim().Equals((typeof(System.Int16)).Name))
            {
                return "INT";
            }
            else if (netType.Trim().Equals((typeof(System.Decimal)).Name))
            {
                return "DOUBLE";
            }
            else if (netType.Trim().Equals((typeof(System.SByte)).Name))
            {
                return "TINYINT";
            }
            else if (netType.Trim().Equals((typeof(System.Double)).Name))
            {
                return "FLOAT";
            }
            else if (netType.Trim().Equals((typeof(System.DateTime)).Name))
            {
                return "DATE";
            }
            else if (netType.Trim().Equals((typeof(System.Single)).Name))
            {
                return "FLOAT";
            }
            else if (netType.Trim().Equals((typeof(System.TimeSpan)).Name))
            {
                return "TIME";
            }
            else if (netType.Trim().ToLower().Equals(DatabaseConstants.LongTextType.ToLower()))
            {
                return DatabaseConstants.LongTextType;
            }
            else
            {
                throw new Exception("MySQL Unsupported Type Exception");
            }
            
        }
    }
}
