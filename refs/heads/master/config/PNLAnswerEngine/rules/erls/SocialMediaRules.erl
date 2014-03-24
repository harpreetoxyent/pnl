<ruleset name="SocialMediaRecommendation">
	<variable-list>
		<variable>
			<type>com.oxymedical.core.commonData.HICData</type>
			<id>dataObject</id>
		</variable>
		<variable>
			<type>com.recommender.datamodel.ISocialMediaObject</type>
			<id>socObject</id>
		</variable>
	</variable-list>
	<rule name="TestRule1">
		<salience> 0 </salience>
		<if>
			<condition-list>
				<condition name="cond1">
					(dataObject.getLocation() == "US")
				</condition>
			</condition-list>
		</if>
		<then>
			<consequence-list>
				<consequence name="c1">
					socObject.addUniURLIntoData()
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
					(dataObject.getLocation() == "US")
				</condition>
			</condition-list>
		</if>
			<then>
				<consequence-list>
					<consequence name="c1">
						socObject.addLocURLIntoData()
					</consequence>
				</consequence-list>
			</then>
			<url> "http://www.google.com" </url>
	</rule>
</ruleset>
