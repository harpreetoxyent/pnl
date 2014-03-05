
using System;
using EIBFormDesigner.Designer;

namespace EIBFormDesigner.Parse
{

    public class Parser
    {
        const int _EOF = 0;
        const int _ident = 1;
        const int _intCon = 2;
        const int _realCon = 3;
        const int _charCon = 4;
        const int _stringCon = 5;
        const int _and = 6;
        const int _assgn = 7;
        const int _colon = 8;
        const int _comma = 9;
        const int _dec = 10;
        const int _div = 11;
        const int _dot = 12;
        const int _eq = 13;
        const int _gt = 14;
        const int _gte = 15;
        const int _inc = 16;
        const int _eqop = 17;
        const int _gtop = 18;
        const int _ltop = 19;
        const int _letop = 20;
        const int _getop = 21;
        const int _lbrace = 22;
        const int _lbrack = 23;
        const int _leftparanthesis = 24;
        const int _lshift = 25;
        const int _lt = 26;
        const int _lte = 27;
        const int _mod = 28;
        const int _neq = 29;
        const int _not = 30;
        const int _or = 31;
        const int _rbrace = 32;
        const int _rbrack = 33;
        const int _rightparanthesis = 34;
        const int _rshift = 35;
        const int _scolon = 36;
        const int _tilde = 37;
        const int _times = 38;
        const int _move = 39;
        const int _insert = 40;
        const int _delete = 41;
        const int _datediff = 42;
        const int _saveform = 43;
        const int _thisv = 44;
        const int _executeaction = 45;
        const int _executeListQuery = 46;
        const int _executeGridQuery = 47;
        const int _executeComboQuery = 48;
        const int _printReport = 49;
        const int _showTree = 50;
        const int _invokeaction = 51;
        const int _showGridQuery = 52;
        const int _executeOnNode = 53;
        const int _updateSession = 54;
        const int _getSessionAttrib = 55;
        const int _createArray = 56;
        const int _printwindow = 57;
        const int _setDataStatus = 58;
        const int _getDate = 59;
        const int _message = 60;
        const int _blockCom = 61;
        const int _lineCom = 62;
        const int _quote = 63;
        const int maxT = 99;

        const bool T = true;
        const bool x = false;
        const int minErrDist = 2;

        public Scanner scanner;
        public Errors errors;

        public Token t;    // last recognized token
        public Token la;   // lookahead token
        int errDist = minErrDist;



        public Parser(Scanner scanner)
        {
            this.scanner = scanner;
            errors = new Errors();
        }

        void SynErr(int n)
        {
            if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
            errDist = 0;
        }

        public void SemErr(string msg)
        {
            if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
            errDist = 0;
        }

        void Get()
        {
            for (; ; )
            {
                t = la;
                la = scanner.Scan();
                if (la.kind <= maxT) { ++errDist; break; }

                la = t;
            }
        }

        void Expect(int n)
        {
            if (la.kind == n) Get(); else { SynErr(n); }
        }

        bool StartOf(int s)
        {
            return set[s, la.kind];
        }

        void ExpectWeak(int n, int follow)
        {
            if (la.kind == n) Get();
            else
            {
                SynErr(n);
                while (!StartOf(follow)) Get();
            }
        }


        bool WeakSeparator(int n, int syFol, int repFol)
        {
            int kind = la.kind;
            if (kind == n) { Get(); return true; }
            else if (StartOf(repFol)) { return false; }
            else
            {
                SynErr(n);
                while (!(set[syFol, kind] || set[repFol, kind] || set[0, kind]))
                {
                    Get();
                    kind = la.kind;
                }
                return StartOf(syFol);
            }
        }


        void Formula()
        {
            statement();
            while (StartOf(1))
            {
                statement();
            }
        }

        void statement()
        {
            if (StartOf(2))
            {
                methodStatementcomplete();
            }
            else if (StartOf(3))
            {
                validStatement();
            }
            else if (StartOf(4))
            {
                cons();
            }
            else if (la.kind == 64)
            {
                conditionStatement();
            }
            else SynErr(100);
        }

        void methodStatementcomplete()
        {
            methodStatement();
            Expect(36);
        }

        void validStatement()
        {
            if (StartOf(5))
            {
                if (StartOf(6))
                {
                    varDeclaration();
                }
                else
                {
                    designator();
                }
            }
            if (la.kind == 7)
            {
                Get();
            }
            validExpression();
            Expect(36);
        }

        void cons()
        {
            AssignOp();
            Expr();
        }

        void conditionStatement()
        {
            ifStatement();
            while (la.kind == 65)
            {
                elseStatement();
            }
        }

        void methodStatement()
        {
            if (la.kind == 40)
            {
                insertStatement();
            }
            else if (la.kind == 41)
            {
                deleteStatement();
            }
            else if (la.kind == 42)
            {
                datedifStatement();
            }
            else if (la.kind == 39)
            {
                moveStatement();
            }
            else if (la.kind == 43)
            {
                saveformStatement();
            }
            else if (la.kind == 57)
            {
                printwindowStatement();
            }
            else if (la.kind == 45)
            {
                executeStatement();
            }
            else if (la.kind == 46)
            {
                exListQueryStatement();
            }
            else if (la.kind == 47)
            {
                exGridQueryStatement();
            }
            else if (la.kind == 48)
            {
                exComboQueryStatement();
            }
            else if (la.kind == 49)
            {
                printReportStatement();
            }
            else if (la.kind == 50)
            {
                showTreeStatement();
            }
            else if (la.kind == 52)
            {
                showgridStatement();
            }
            else if (la.kind == 51)
            {
                invokeStatement();
            }
            else if (la.kind == 57)
            {
                printwindowStatement();
            }
            else if (la.kind == 53)
            {
                executeOnNodeStatement();
            }
            else if (la.kind == 54)
            {
                updateSessionStatement();
            }
            else if (la.kind == 55)
            {
                getSessionAttribStatement();
            }
            else if (la.kind == 58)
            {
                setDataStatusStatement();
            }
            else if (la.kind == 60)
            {
                messageStatement();
            }
            else SynErr(101);
        }

        void insertStatement()
        {
            Expect(40);
            Expect(24);
            queryExpr();
            Expect(9);
            queryExpr();
            Expect(9);
            Expect(2);
            Expect(34);
        }

        void deleteStatement()
        {
            Expect(41);
            Expect(24);
            queryExpr();
            Expect(9);
            Expect(2);
            Expect(9);
            Expect(2);
            Expect(34);
        }

        void datedifStatement()
        {
            Expect(42);
            Expect(24);
            queryExpr();
            Expect(9);
            queryExpr();
            Expect(9);
            queryExpr();
            Expect(34);
        }

        void moveStatement()
        {
            Expect(39);
            Expect(24);
            queryExpr();
            Expect(34);
        }

        void saveformStatement()
        {
            Expect(43);
            Expect(24);
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            Expect(34);
        }

        void printwindowStatement()
        {
            Expect(57);
            Expect(24);
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            Expect(34);
        }

        void executeStatement()
        {
            Expect(45);
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            if (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 69 || la.kind == 70)
            {
                addOp();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void exListQueryStatement()
        {
            Expect(46);
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            if (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 69 || la.kind == 70)
            {
                addOp();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void exGridQueryStatement()
        {
            Expect(47);
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            if (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 69 || la.kind == 70)
            {
                addOp();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void exComboQueryStatement()
        {
            Expect(48);
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            if (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 69 || la.kind == 70)
            {
                addOp();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void printReportStatement()
        {
            Expect(49);
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            if (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 69 || la.kind == 70)
            {
                addOp();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void showTreeStatement()
        {
            Expect(50);
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            if (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void showgridStatement()
        {
            Expect(52);
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void invokeStatement()
        {
            Expect(51);
            Expect(24);
            queryExpr();
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            while (la.kind == 69 || la.kind == 70)
            {
                addOp();
                queryExpr();
            }
            Expect(34);
        }

        void executeOnNodeStatement()
        {
            Expect(53);
            Expect(24);
            queryExpr();
            Expect(9);
            eventNodeStatement();
            Expect(9);
            while (StartOf(2))
            {
                methodStatementcomplete();
            }
            Expect(34);
        }

        void updateSessionStatement()
        {
            Expect(54);
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void getSessionAttribStatement()
        {
            Expect(55);
            if (la.kind == 24)
            {
                Get();
            }
            queryExpr();
            if (la.kind == 34)
            {
                Get();
            }
        }

        void setDataStatusStatement()
        {
            Expect(58);
            if (la.kind == 24)
            {
                Get();
            }
            queryExpr();
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void messageStatement()
        {
            Expect(60);
            Expect(24);
            queryExpr();
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            Expect(34);
        }

        void ifStatement()
        {
            Expect(64);
            Expect(24);
            expression();
            Expect(34);
            Expect(22);
            statement();
            while (StartOf(1))
            {
                statement();
            }
            Expect(32);
        }

        void elseStatement()
        {
            Expect(65);
            if (la.kind == 64)
            {
                Get();
                Expect(24);
                expression();
                Expect(34);
            }
            Expect(22);
            statement();
            while (StartOf(1))
            {
                statement();
            }
            Expect(32);
        }

        void expression()
        {
            simexpr();
            if (StartOf(7))
            {
                relOp();
                if (la.kind == 2 || la.kind == 3 || la.kind == 4)
                {
                    Literal();
                }
                else if (la.kind == 1 || la.kind == 2 || la.kind == 5)
                {
                    queryExpr();
                }
                else if (StartOf(8))
                {
                    simexpr();
                }
                else SynErr(102);
                while (!(StartOf(9))) { SynErr(103); Get(); }
            }
        }

        void varDeclaration()
        {
            validDataType();
            Expect(1);
        }

        void designator()
        {
            Expect(1);
            while (la.kind == 12 || la.kind == 23 || la.kind == 68)
            {
                if (la.kind == 23)
                {
                    Get();
                    expList();
                    Expect(33);
                }
                else if (la.kind == 68)
                {
                    Get();
                }
                else
                {
                    Get();
                    Expect(1);
                }
            }
        }

        void validExpression()
        {
            if (la.kind == 40)
            {
                insertStatement();
            }
            else if (la.kind == 44)
            {
                thisStatement();
            }
            else if (la.kind == 42)
            {
                datedifStatement();
            }
            else if (la.kind == 41)
            {
                deleteStatement();
            }
            else if (la.kind == 56)
            {
                createArrayExpr();
            }
            else if (la.kind == 59)
            {
                getDateStatement();
            }
            else if (la.kind == 1)
            {
                anyStatement();
            }
            else if (StartOf(8))
            {
                expression();
            }
            else if (la.kind == 51)
            {
                invokeStatement();
            }
            else SynErr(104);
        }

        void thisStatement()
        {
            Expect(44);
            while (la.kind == 12)
            {
                Get();
                Expect(1);
            }
            if (la.kind == 7)
            {
                Get();
            }
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
                while (StartOf(10))
                {
                    if (la.kind == 17)
                    {
                        Get();
                    }
                    if (la.kind == 9)
                    {
                        Get();
                    }
                    queryExpr();
                }
            }
            if (la.kind == 34)
            {
                Get();
            }
            if (la.kind == 69 || la.kind == 70)
            {
                addOp();
                queryExpr();
                while (la.kind == 69 || la.kind == 70)
                {
                    addOp();
                    queryExpr();
                }
            }
        }

        void createArrayExpr()
        {
            Expect(56);
            Expect(24);
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            else if (StartOf(6))
            {
                validDataType();
            }
            else SynErr(105);
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            Expect(34);
        }

        void getDateStatement()
        {
            Expect(59);
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void anyStatement()
        {
            Expect(1);
            while (la.kind == 12)
            {
                Get();
                Expect(1);
            }
            if (la.kind == 24)
            {
                Get();
            }
            if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            while (la.kind == 9)
            {
                Get();
                queryExpr();
            }
            if (la.kind == 34)
            {
                Get();
            }
        }

        void queryExpr()
        {
            if (la.kind == 5)
            {
                Get();
            }
            else if (la.kind == 1 || la.kind == 2)
            {
                Term();
            }
            else SynErr(106);
        }

        void addOp()
        {
            if (la.kind == 69)
            {
                Get();
            }
            else if (la.kind == 70)
            {
                Get();
            }
            else SynErr(107);
        }

        void eventNodeStatement()
        {
            if (la.kind == 66)
            {
                Get();
            }
            else if (la.kind == 67)
            {
                Get();
            }
            else SynErr(108);
        }

        void validDataType()
        {
            switch (la.kind)
            {
                case 75:
                    {
                        Get();
                        break;
                    }
                case 76:
                    {
                        Get();
                        break;
                    }
                case 77:
                    {
                        Get();
                        break;
                    }
                case 78:
                    {
                        Get();
                        break;
                    }
                case 79:
                    {
                        Get();
                        break;
                    }
                case 80:
                    {
                        Get();
                        break;
                    }
                case 81:
                    {
                        Get();
                        break;
                    }
                case 82:
                    {
                        Get();
                        break;
                    }
                case 83:
                    {
                        Get();
                        break;
                    }
                case 84:
                    {
                        Get();
                        break;
                    }
                case 85:
                    {
                        Get();
                        break;
                    }
                default: SynErr(109); break;
            }
        }

        void expList()
        {
            expression();
            while (la.kind == 9)
            {
                Get();
                expression();
            }
        }

        void simexpr()
        {
            while (!(StartOf(11))) { SynErr(110); Get(); }
            if (la.kind == 69 || la.kind == 70)
            {
                if (la.kind == 69)
                {
                    Get();
                }
                else
                {
                    Get();
                }
            }
            simpleterm();
            while (la.kind == 69 || la.kind == 70)
            {
                addOp();
                simpleterm();
            }
        }

        void relOp()
        {
            switch (la.kind)
            {
                case 74:
                    {
                        Get();
                        break;
                    }
                case 26:
                    {
                        Get();
                        break;
                    }
                case 27:
                    {
                        Get();
                        break;
                    }
                case 14:
                    {
                        Get();
                        break;
                    }
                case 15:
                    {
                        Get();
                        break;
                    }
                case 29:
                    {
                        Get();
                        break;
                    }
                case 13:
                    {
                        Get();
                        break;
                    }
                default: SynErr(111); break;
            }
        }

        void Literal()
        {
            if (la.kind == 2)
            {
                Get();
            }
            else if (la.kind == 3)
            {
                Get();
            }
            else if (la.kind == 4)
            {
                Get();
            }
            else SynErr(112);
        }

        void simpleterm()
        {
            factor();
            while (StartOf(12))
            {
                mulOp();
                factor();
            }
        }

        void factor()
        {
            while (!(StartOf(13))) { SynErr(113); Get(); }
            if (la.kind == 2)
            {
                Get();
            }
            else if (la.kind == 1)
            {
                Get();
                while (la.kind == 12 || la.kind == 23 || la.kind == 68)
                {
                    if (la.kind == 23)
                    {
                        Get();
                        expList();
                        Expect(33);
                    }
                    else if (la.kind == 68)
                    {
                        Get();
                    }
                    else
                    {
                        Get();
                        Expect(1);
                    }
                }
            }
            else if (la.kind == 1 || la.kind == 2 || la.kind == 5)
            {
                queryExpr();
            }
            else if (la.kind == 24)
            {
                Get();
                expression();
                while (la.kind == 9)
                {
                    Get();
                    expression();
                }
                Expect(34);
            }
            else if (la.kind == 63)
            {
                Get();
                if (StartOf(8))
                {
                    expression();
                }
                if (la.kind == 28)
                {
                    Get();
                }
                if (la.kind == 17)
                {
                    Get();
                }
                if (la.kind == 18)
                {
                    Get();
                }
                if (la.kind == 19)
                {
                    Get();
                }
                if (la.kind == 20)
                {
                    Get();
                }
                Expect(63);
            }
            else if (la.kind == 30 || la.kind == 37)
            {
                if (la.kind == 30)
                {
                    Get();
                }
                else
                {
                    Get();
                }
                factor();
            }
            else SynErr(114);
        }

        void mulOp()
        {
            if (la.kind == 38)
            {
                Get();
            }
            else if (la.kind == 11)
            {
                Get();
            }
            else if (la.kind == 71)
            {
                Get();
            }
            else if (la.kind == 72)
            {
                Get();
            }
            else if (la.kind == 73)
            {
                Get();
            }
            else SynErr(115);
        }

        void Term()
        {
            if (la.kind == 1)
            {
                Get();
            }
            else if (la.kind == 2)
            {
                Get();
            }
            else SynErr(116);
            while (la.kind == 12)
            {
                Get();
                Expect(1);
            }
        }

        void AssignOp()
        {
            switch (la.kind)
            {
                case 7:
                    {
                        Get();
                        break;
                    }
                case 86:
                    {
                        Get();
                        break;
                    }
                case 87:
                    {
                        Get();
                        break;
                    }
                case 88:
                    {
                        Get();
                        break;
                    }
                case 89:
                    {
                        Get();
                        break;
                    }
                case 90:
                    {
                        Get();
                        break;
                    }
                case 91:
                    {
                        Get();
                        break;
                    }
                case 92:
                    {
                        Get();
                        break;
                    }
                case 93:
                    {
                        Get();
                        break;
                    }
                case 94:
                    {
                        Get();
                        break;
                    }
                case 95:
                    {
                        Get();
                        break;
                    }
                default: SynErr(117); break;
            }
        }

        void Expr()
        {
            Unary();
            if (StartOf(14))
            {
                OrExpr();
                if (la.kind == 96)
                {
                    Get();
                    Expr();
                    Expect(8);
                    Expr();
                }
            }
            else if (StartOf(4))
            {
                AssignOp();
                Expr();
            }
            else SynErr(118);
        }

        void Unary()
        {
            while (StartOf(15))
            {
                switch (la.kind)
                {
                    case 69:
                        {
                            Get();
                            break;
                        }
                    case 70:
                        {
                            Get();
                            break;
                        }
                    case 30:
                        {
                            Get();
                            break;
                        }
                    case 37:
                        {
                            Get();
                            break;
                        }
                    case 38:
                        {
                            Get();
                            break;
                        }
                    case 16:
                        {
                            Get();
                            break;
                        }
                    case 10:
                        {
                            Get();
                            break;
                        }
                    case 6:
                        {
                            Get();
                            break;
                        }
                }
            }
            Primary();
        }

        void OrExpr()
        {
            AndExpr();
            while (la.kind == 97)
            {
                Get();
                Unary();
                AndExpr();
            }
        }

        void AndExpr()
        {
            BitOrExpr();
            while (la.kind == 98)
            {
                Get();
                Unary();
                BitOrExpr();
            }
        }

        void BitOrExpr()
        {
            BitXorExpr();
            while (la.kind == 31)
            {
                Get();
                Unary();
                BitXorExpr();
            }
        }

        void BitXorExpr()
        {
            BitAndExpr();
            while (la.kind == 68)
            {
                Get();
                Unary();
                BitAndExpr();
            }
        }

        void BitAndExpr()
        {
            EqlExpr();
            while (la.kind == 6)
            {
                Get();
                Unary();
                EqlExpr();
            }
        }

        void EqlExpr()
        {
            RelExpr();
            while (la.kind == 13 || la.kind == 29)
            {
                if (la.kind == 29)
                {
                    Get();
                }
                else
                {
                    Get();
                }
                Unary();
                RelExpr();
            }
        }

        void RelExpr()
        {
            ShiftExpr();
            while (StartOf(16))
            {
                if (la.kind == 26)
                {
                    Get();
                }
                else if (la.kind == 14)
                {
                    Get();
                }
                else if (la.kind == 27)
                {
                    Get();
                }
                else
                {
                    Get();
                }
                Unary();
                ShiftExpr();
            }
        }

        void ShiftExpr()
        {
            AddExpr();
            while (la.kind == 25 || la.kind == 35)
            {
                if (la.kind == 25)
                {
                    Get();
                }
                else
                {
                    Get();
                }
                Unary();
                AddExpr();
            }
        }

        void AddExpr()
        {
            MulExpr();
            while (la.kind == 69 || la.kind == 70)
            {
                if (la.kind == 69)
                {
                    Get();
                }
                else
                {
                    Get();
                }
                Unary();
                MulExpr();
            }
        }

        void MulExpr()
        {
            while (la.kind == 11 || la.kind == 28 || la.kind == 38)
            {
                if (la.kind == 38)
                {
                    Get();
                }
                else if (la.kind == 11)
                {
                    Get();
                }
                else
                {
                    Get();
                }
                Unary();
            }
        }

        void Primary()
        {
            if (la.kind == 1)
            {
                Get();
            }
            else if (la.kind == 2 || la.kind == 3 || la.kind == 4)
            {
                Literal();
            }
            else if (la.kind == 24)
            {
                Get();
                Expr();
                Expect(34);
            }
            else if (la.kind == 44)
            {
                Get();
                while (StartOf(17))
                {
                    if (la.kind == 16)
                    {
                        Get();
                    }
                    else if (la.kind == 10)
                    {
                        Get();
                    }
                    else if (la.kind == 12)
                    {
                        Get();
                        Expect(1);
                    }
                    else
                    {
                        Get();
                        if (StartOf(18))
                        {
                            Expr();
                            while (la.kind == 9)
                            {
                                Get();
                                Expr();
                            }
                        }
                        Expect(34);
                    }
                }
            }
            else SynErr(119);
        }



        public void Parse()
        {
            la = new Token();
            la.val = "";
            Get();
            Formula();

            Expect(0);
        }

        bool[,] set = {
		{T,T,T,x, x,T,x,x, x,T,x,x, x,x,x,x, x,T,T,T, T,x,x,x, T,x,x,x, T,x,T,x, x,T,T,x, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,T,T,x, x,T,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, x,x,T,x, x,x,x,x, x,T,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x,x,T, T,x,x,x, x,T,T,x, x,x,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, x,x,x,x, x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, x,T,T,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,T,T,x, x,T,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, x,x,T,x, x,x,x,x, x,T,x,x, T,T,T,x, T,x,x,x, x,x,x,T, x,x,x,x, T,x,x,T, x,x,x,T, x,x,x,x, x,T,T,x, x,x,x,T, T,T,T,T, T,T,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,T, T,T,T,T, T,T,T,T, x,x,x,x, x},
		{x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,T, x,x,x,x, x,x,x,x, x,x,T,T, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,T,T,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, x,x,T,x, x,x,x,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{T,x,x,x, x,x,x,x, x,T,x,x, x,x,x,x, x,T,T,T, T,x,x,x, x,x,x,x, T,x,x,x, x,T,T,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,T,T,x, x,T,x,x, x,T,x,x, x,x,x,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{T,T,T,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, x,x,T,x, x,x,x,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{T,T,T,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, x,x,T,x, x,x,x,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{T,T,T,x, x,T,T,T, T,T,x,T, x,T,T,T, x,x,x,x, x,x,x,x, T,T,T,T, T,T,T,T, T,x,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x,x,T, T,x,x,x, T,T,T,x, x,x,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,x, x},
		{x,x,x,x, x,x,T,x, x,x,T,x, x,x,x,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x,x,x, x,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,T, x,x,x,x, x,x,x,x, x,x,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,x,x,x, x,x,x,x, x,x,T,x, T,x,x,x, T,x,x,x, x,x,x,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,T,T,T, T,x,T,x, x,x,T,x, x,x,x,x, T,x,x,x, x,x,x,x, T,x,x,x, x,x,T,x, x,x,x,x, x,T,T,x, x,x,x,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x}

	};
    } // end Parser


    public class Errors
    {
        public int count = 0;                                    // number of errors detected
        //public System.IO.TextWriter errorStream = Console.Out;   // error messages go to this stream
        public System.IO.TextWriter errorStream = FormulaEditorWindow.errorWriter; // error messages go to this stream
        public string errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text

        public void SynErr(int line, int col, int n)
        {
            string s;
            switch (n)
            {
                case 0: s = "EOF expected"; break;
                case 1: s = "ident expected"; break;
                case 2: s = "intCon expected"; break;
                case 3: s = "realCon expected"; break;
                case 4: s = "charCon expected"; break;
                case 5: s = "stringCon expected"; break;
                case 6: s = "and expected"; break;
                case 7: s = "assgn expected"; break;
                case 8: s = "colon expected"; break;
                case 9: s = "comma expected"; break;
                case 10: s = "dec expected"; break;
                case 11: s = "div expected"; break;
                case 12: s = "dot expected"; break;
                case 13: s = "eq expected"; break;
                case 14: s = "gt expected"; break;
                case 15: s = "gte expected"; break;
                case 16: s = "inc expected"; break;
                case 17: s = "eqop expected"; break;
                case 18: s = "gtop expected"; break;
                case 19: s = "ltop expected"; break;
                case 20: s = "letop expected"; break;
                case 21: s = "getop expected"; break;
                case 22: s = "lbrace expected"; break;
                case 23: s = "lbrack expected"; break;
                case 24: s = "leftparanthesis expected"; break;
                case 25: s = "lshift expected"; break;
                case 26: s = "lt expected"; break;
                case 27: s = "lte expected"; break;
                case 28: s = "mod expected"; break;
                case 29: s = "neq expected"; break;
                case 30: s = "not expected"; break;
                case 31: s = "or expected"; break;
                case 32: s = "rbrace expected"; break;
                case 33: s = "rbrack expected"; break;
                case 34: s = "rightparanthesis expected"; break;
                case 35: s = "rshift expected"; break;
                case 36: s = "scolon expected"; break;
                case 37: s = "tilde expected"; break;
                case 38: s = "times expected"; break;
                case 39: s = "move expected"; break;
                case 40: s = "insert expected"; break;
                case 41: s = "delete expected"; break;
                case 42: s = "datediff expected"; break;
                case 43: s = "saveform expected"; break;
                case 44: s = "thisv expected"; break;
                case 45: s = "executeaction expected"; break;
                case 46: s = "executeListQuery expected"; break;
                case 47: s = "executeGridQuery expected"; break;
                case 48: s = "executeComboQuery expected"; break;
                case 49: s = "printReport expected"; break;
                case 50: s = "showTree expected"; break;
                case 51: s = "invokeaction expected"; break;
                case 52: s = "showGridQuery expected"; break;
                case 53: s = "executeOnNode expected"; break;
                case 54: s = "updateSession expected"; break;
                case 55: s = "getSessionAttrib expected"; break;
                case 56: s = "createArray expected"; break;
                case 57: s = "printwindow expected"; break;
                case 58: s = "setDataStatus expected"; break;
                case 59: s = "getDate expected"; break;
                case 60: s = "message expected"; break;
                case 61: s = "blockCom expected"; break;
                case 62: s = "lineCom expected"; break;
                case 63: s = "quote expected"; break;
                case 64: s = "\"if\" expected"; break;
                case 65: s = "\"else\" expected"; break;
                case 66: s = "\"onclick\" expected"; break;
                case 67: s = "\"ondoubleclick\" expected"; break;
                case 68: s = "\"^\" expected"; break;
                case 69: s = "\"+\" expected"; break;
                case 70: s = "\"-\" expected"; break;
                case 71: s = "\"div\" expected"; break;
                case 72: s = "\"rem\" expected"; break;
                case 73: s = "\"mod\" expected"; break;
                case 74: s = "\"<>\" expected"; break;
                case 75: s = "\"string\" expected"; break;
                case 76: s = "\"int\" expected"; break;
                case 77: s = "\"float\" expected"; break;
                case 78: s = "\"boolean\" expected"; break;
                case 79: s = "\"string[]\" expected"; break;
                case 80: s = "\"byte\" expected"; break;
                case 81: s = "\"short\" expected"; break;
                case 82: s = "\"long\" expected"; break;
                case 83: s = "\"char\" expected"; break;
                case 84: s = "\"time\" expected"; break;
                case 85: s = "\"date\" expected"; break;
                case 86: s = "\"+=\" expected"; break;
                case 87: s = "\"-=\" expected"; break;
                case 88: s = "\"*=\" expected"; break;
                case 89: s = "\"/=\" expected"; break;
                case 90: s = "\"%=\" expected"; break;
                case 91: s = "\"&=\" expected"; break;
                case 92: s = "\"|=\" expected"; break;
                case 93: s = "\"^=\" expected"; break;
                case 94: s = "\"<<=\" expected"; break;
                case 95: s = "\">>=\" expected"; break;
                case 96: s = "\"?\" expected"; break;
                case 97: s = "\"||\" expected"; break;
                case 98: s = "\"&&\" expected"; break;
                case 99: s = "??? expected"; break;
                case 100: s = "invalid statement"; break;
                case 101: s = "invalid methodStatement"; break;
                case 102: s = "invalid expression"; break;
                case 103: s = "this symbol not expected in expression"; break;
                case 104: s = "invalid validExpression"; break;
                case 105: s = "invalid createArrayExpr"; break;
                case 106: s = "invalid queryExpr"; break;
                case 107: s = "invalid addOp"; break;
                case 108: s = "invalid eventNodeStatement"; break;
                case 109: s = "invalid validDataType"; break;
                case 110: s = "this symbol not expected in simexpr"; break;
                case 111: s = "invalid relOp"; break;
                case 112: s = "invalid Literal"; break;
                case 113: s = "this symbol not expected in factor"; break;
                case 114: s = "invalid factor"; break;
                case 115: s = "invalid mulOp"; break;
                case 116: s = "invalid Term"; break;
                case 117: s = "invalid AssignOp"; break;
                case 118: s = "invalid Expr"; break;
                case 119: s = "invalid Primary"; break;

                default: s = "error " + n; break;
            }
            errorStream.WriteLine(errMsgFormat, line, col, s);
            count++;
        }

        public void SemErr(int line, int col, string s)
        {
            errorStream.WriteLine(errMsgFormat, line, col, s);
            count++;
        }

        public void SemErr(string s)
        {
            errorStream.WriteLine(s);
            count++;
        }

        public void Warning(int line, int col, string s)
        {
            errorStream.WriteLine(errMsgFormat, line, col, s);
        }

        public void Warning(string s)
        {
            errorStream.WriteLine(s);
        }
    } // Errors


    public class FatalError : Exception
    {
        public FatalError(string m) : base(m) { }
    }

}