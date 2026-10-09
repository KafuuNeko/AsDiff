# Releasing AsDiff

## Prerequisites

- A JetBrains Marketplace Vendor profile
- A Marketplace token from `Profile > My Tokens`
- A locally generated signing certificate and private key
- JDK 21 or newer

Never commit the Marketplace token, private key, certificate password, or environment files.

## Environment

Signing files are generated locally under the ignored `.release/` directory:

```text
.release/chain.crt
.release/private.pem
.release/signing-password
```

`PRIVATE_KEY_PASSWORD` can override the local password file. `PUBLISH_TOKEN`
contains the JetBrains Marketplace personal token used for later automated updates.

## First Release

JetBrains requires the first release to be uploaded manually:

```bash
./gradlew clean test verifyPlugin signPlugin
```

Upload the signed ZIP from `build/distributions/` at https://plugins.jetbrains.com/author/me.

## Updates

Marketplace does not accept the same version twice. Update the version in `build.gradle.kts`, then run:

```bash
./gradlew clean test verifyPlugin publishPlugin
```
