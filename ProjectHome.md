The overall goal with this project is to create and maintain reusable Java components.

## Module for authentication with a Swedish e-identity ##
This module is intended to be used with Spring Security to allow users to login with a Swedish e-identity. The identity provider is Logica which hosts the idp solution.

[Technical documentation](UsingSAMLAuthenticationModule.md)

## HSA Auth Module ##
This module is intended to be used with Spring Security and allow users to login with a SITHS card and a card reader. After a successful authentication the user's roleset will be retreived from the HSA-directory (HSA is a national directory for all personnel within  Swedish health care).

[Technical documentation](SITHSAuthenticationModule.md)

## MVK Integration Module ##
This is a simple extension to Spring security which simplifies SSO with MVK (Mina vårdkontakter). The module can be used from any Spring based Java application that will use MVK's Caia service.

[Technical documentation](mvk_integration.md)