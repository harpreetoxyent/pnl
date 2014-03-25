<ruleset name="SocialMediaRecommendation">
	<variable-list>
		<variable>
			<type>com.oxymedical.core.commonData.HICData</type>
			<id>dataObject</id>
		</variable>
		<variable>
			<type>com.pnl.component.socialmedia.SocialMediaComponent</type>
			<id>socialObject</id>
		</variable>
	</variable-list>
	<rule name="TestRule1">
		<salience> 0 </salience>
		<if>
			<condition-list>
				<condition name="cond1">
					(dataObject.getUniqueID() == "25")
				</condition>
			</condition-list>
		</if>
		<then>
			<consequence-list>
				<consequence name="c1">
					socialObject.addUnivURLToAnswerData()
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
						socialObject.addTouURLToAnswerData()
					</consequence>
				</consequence-list>
			</then>
			<url> "http://www.google.com" </url>
	</rule>
</ruleset>
