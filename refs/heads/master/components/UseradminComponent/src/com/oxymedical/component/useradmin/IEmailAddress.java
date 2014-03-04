package com.oxymedical.component.useradmin;

public interface IEmailAddress 
{
	public com.oxymedical.component.useradmin.model.Emailaddress addEmailAddress(
			java.lang.String userId, java.lang.String className,
			java.lang.String classPK, java.lang.String address,
			java.lang.String typeId, boolean primary);

		public void deleteEmailAddress(java.lang.String emailAddressId);

		public void deleteEmailAddresses(java.lang.String companyId,
			java.lang.String className, java.lang.String classPK);
		public com.oxymedical.component.useradmin.model.Emailaddress getEmailAddress(
			java.lang.String emailAddressId);

		public java.util.List getEmailAddresses(java.lang.String companyId,
			java.lang.String className, java.lang.String classPK);

		public com.oxymedical.component.useradmin.model.Emailaddress updateEmailAddress(
			java.lang.String emailAddressId, java.lang.String address,
			java.lang.String typeId, boolean primary);
}
