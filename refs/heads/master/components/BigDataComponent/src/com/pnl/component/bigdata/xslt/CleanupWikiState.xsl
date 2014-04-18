<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:gl="http://ananas.org/2003/tips/gallery"
                xmlns:html="http://www.w3.org/1999/xhtml"
                exclude-result-prefixes="html">

<xsl:output method="xml" indent="yes" encoding="MacRoman"/>
<xsl:strip-space elements="*"/>
<xsl:template match="html:html">
  <add>
   <doc>
    <field name="id">
      <xsl:value-of select="html:body/html:div[3]/html:h1"/>
    </field>
    <field name="url">
      <xsl:value-of select="html:head/html:url"/>
    </field>
    <field name="content">
      <xsl:value-of select="html:body"/>
    </field>
    <field name="title">
      <xsl:value-of select="html:head/html:title"/>
    </field>
    <field name="name">
      <xsl:value-of select="html:body/html:div[3]/html:div[3]/html:div[4]/html:table/html:tr[1]/html:th"/>
    </field>
    <field name="flag">
      <xsl:value-of select="concat('http:',html:body/html:div[3]/html:div[3]/html:div[4]/html:table/html:tr[2]/html:td[1]/html:table/html:tr[1]/html:td[1]/html:a/html:img/@src)"/> 
    </field>
    <field name="capital">
      <xsl:value-of select="html:body/html:div[3]/html:div[3]/html:div[4]/html:table/html:tr[8]/html:td"/> 
    </field>
    <field name="largest_city">
      <xsl:value-of select="html:body/html:div[3]/html:div[3]/html:div[4]/html:table/html:tr[9]/html:td"/> 
    </field>
    <field name="governor">
      <xsl:value-of select="html:body/html:div[3]/html:div[3]/html:div[4]/html:table/html:tr[28]/html:td"/> 
    </field>
    <field name="lieutenant_governor">
      <xsl:value-of select="html:body/html:div[3]/html:div[3]/html:div[4]/html:table/html:tr[29]/html:td"/> 
    </field>
    <field name="senators">
      <xsl:value-of select="html:body/html:div[3]/html:div[3]/html:div[4]/html:table/html:tr[33]/html:td"/> 
    </field>
    <field name="us_house_delegation">
      <xsl:value-of select="html:body/html:div[3]/html:div[3]/html:div[4]/html:table/html:tr[34]/html:td"/> 
    </field>
    <field name="time_zone">
      <xsl:value-of select="html:body/html:div[3]/html:div[3]/html:div[4]/html:table/html:tr[35]/html:td"/> 
    </field>
    <field name="website">
      <xsl:value-of select="html:body/html:div[3]/html:div[3]/html:div[4]/html:table/html:tr[37]/html:td"/> 
    </field>
    <field name="short_description">
      <xsl:value-of select="html:body/html:div[3]/html:div[3]/html:div[4]/html:p"/> 
    </field>
   </doc>
  </add>
</xsl:template>

</xsl:stylesheet>
