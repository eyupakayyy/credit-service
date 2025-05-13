package com.banking.ing.credit.creditservice.security.util;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.security.Keys.secretKeyFor;

import com.banking.ing.credit.creditservice.common.util.base.Utility;
import java.util.Base64;

public final class SecretKeyUtil extends Utility {

  public static String generateStringKey() {
    return Base64.getEncoder().encodeToString(secretKeyFor(HS256).getEncoded());
  }

}
