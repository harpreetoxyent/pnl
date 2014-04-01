<ruleset name="SocialMediaRecommendation">
	<variable-list>
		<variable>
			<type>com.oxymedical.core.commonData.HICData</type>
			<id>dataObject</id>
		</variable>
	</variable-list>
	<rule name="TestRule1">
		<salience> 0 </salience>
		<if>
			<condition-list>
				<condition name="cond1">
					(dataObject.getUniqueID() == "2014")
				</condition>
			</condition-list>
		</if>
		<then>
			<consequence-list>
				<consequence name="c1">
					dataObject.getUniv_urls()
				</consequence>
			</consequence-list>
			</then>
		<url> "http://www.google.com" </url>
	</rule>
	<rule name="TestRule2">
		<salience> 0 </salience>
		<if>
			<condition-list>
				<condition name="cond1">
					(dataObject.getUniqueID() == "57")
				</condition>
			</condition-list>
		</if>
			<then>
				<consequence-list>
					<consequence name="c1">
						dataObject.getTourist_urls()
					</consequence>
				</consequence-list>
			</then>
			<url> "http://www.google.com" </url>
	</rule>
</ruleset>
