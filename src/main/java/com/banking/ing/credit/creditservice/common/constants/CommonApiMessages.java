package com.banking.ing.credit.creditservice.common.constants;

public final class CommonApiMessages {

  public static final String MESSAGE_DEFAULT_RESOURCE_SUCCESSFULLY_UPDATED = "Resource successfully updated!";
  public static final String MESSAGE_DEFAULT_RESOURCE_ALREADY_EXISTS = "Resource already exists!";
  public static final String MESSAGE_DEFAULT_RESOURCE_NOT_FOUND = "Resource not found!";
  public static final String MESSAGE_UNEXPECTED_ERROR = "Unexpected error";
  public static final String MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_UPDATED_WITH_PARAMETER = "%s successfully updated!";
  public static final String MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_CREATED_WITH_PARAMETER = "%s successfully created!";
  public static final String MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_DELETED_WITH_PARAMETER = "%s successfully deleted!";
  public static final String MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER = "%s  %s found!";
  public static final String MESSAGE_DEFAULT_RESOURCE_FOUND_WITH_COUNT = " %s resources found!";
  public static final String MESSAGE_AUTHENTICATION_TOKEN_EXPIRED = "Authentication token expired, please re-login to continue.";
  public static final String MESSAGE_AUTHENTICATION_NO_RIGHT_TO_ACCESS_THIS_ENTRY_POINT = "You're not authorised to perform this operation.";
  public static final String MESSAGE_AUTHENTICATION_FAILED_DUE_TO_WRONG_PASSWORD = "You're password is incorrect!";
  public static final String MESSAGE_AUTHENTICATION_FAILED = "Your authentication failed!";
  public static final String MESSAGE_NO_ACTIVE_USER_FOUND_MIGHT_BE_DELETED = "No active user found, might be deleted!";
  public static final String MESSAGE_DEFAULT_CREDIT_FAILURE = "Credit application rejected!";
  public static final String MESSAGE_DEFAULT_CREDIT_FAILURE_DUE_TO_INVALID_INTEREST_REQUEST = "The interest rate is not applicable!";
  public static final String MESSAGE_DEFAULT_CREDIT_FAILURE_DUE_TO_INVALID_INSTALLMENT_REQUEST = "The installment count is not applicable!";
  public static final String MESSAGE_DEFAULT_CUSTOMER_CREDIT_DEFINITION_FAILED = "Customer credit definition is not yet fulfilled!";
  public static final String MESSAGE_DEFAULT_CUSTOMER_CREDIT_INSTALLMENT_PAYMENT_DONE_SUCCESSFULLY = "Payment of Customer credit loan installments (%s) successfully done!";


  private CommonApiMessages() {
    throw new UnsupportedOperationException("Constants classes cannot be initialized.");
  }
}
