# q4c - queries for collections

## Installation
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
```xml
<dependency>
    <groupId>io.github.tamingj.q4c</groupId>
    <artifactId>q4c</artifactId>
    <version>${tamingj.q4c.version}</version>
</dependency>
```

## Usage
> Examples

```java
var parentAccounts = accountClient.getParentAccounts();
var childAccounts = accountClient.getChildAccounts();

selectFrom(parentAccounts)
    .fullOuterJoin(childAccounts)
    .on(Account::id, Account::parentId)
    .where(this::parentHasChild)
    .stream()
    .map(this::createParentChildRelation)
    .toList()
// [...]
```

## Developer Preparation (as a publisher)
> Following these Guides:
> 
> [![Sonatype-GPG](https://img.shields.io/badge/Sonatype%20GPG-1B1C30?style=for-the-badge&logo=sonatype)](https://central.sonatype.org/publish/requirements/gpg/#supported-code-hosting-services-for-personal-groupid)
> [![Sonatype-Publishing](https://img.shields.io/badge/Sonatype%20Publishing-1B1C30?style=for-the-badge&logo=sonatype)](https://central.sonatype.org/publish/publish-portal-maven/)

1. Generate a GPG key
```shell
gpg --gen-key
# enter real name
# enter email
# copy 'pub' key
```
2. Submit your public key to a valid keyserver [`keyserver.ubuntu.com`, `keys.openpgp.org`, `pgp.mit.edu`]
```shell
gpg --keyserver keyserver.ubuntu.com --send-keys <PUB_KEY>
```
3. Validate: Next time you run
```shell
mvn clean verify
```
it should look like this:
```shell
#[...]
[INFO] --- maven-gpg-plugin:3.2.4:sign (sign-artifacts) @ q4c ---
[INFO] Signer 'gpg' is signing 4 files with key default
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
#[...]
```
4. Create Sonatype Account if not exists.

[![Sonatype-Account](https://img.shields.io/badge/Sonatype%20Account-1B1C30?style=for-the-badge&logo=sonatype)](https://central.sonatype.com/account)
5. Make sure you have publishing permissions to Namespace: `io.github.tamingj`.
>(requires being added by one of the owners)

[![Sonatype-Namespaces](https://img.shields.io/badge/Sonatype%20Namespaces-1B1C30?style=for-the-badge&logo=sonatype)](https://central.sonatype.com/publishing/namespaces)
6. Generate a User Token for publishing via maven. Click `Generate User Token` on Sonatype Account Page.

[![Sonatype-Account](https://img.shields.io/badge/Sonatype%20Account-1B1C30?style=for-the-badge&logo=sonatype)](https://central.sonatype.com/account)
7. Create `settings.xml` in your `~/.m2` directory which contains server configuration for central.
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <server>
            <id>central</id>
            <username>${SONATYPE_USER_TOKEN_USERNAME}</username>
            <password>${SONATYPE_USER_TOKEN_PASSWORD}</password>
        </server>
    </servers>
</settings>
```


## Contributors
[![Contributors](https://contrib.rocks/image?repo=tamingj/q4c)](https://github.com/tamingj/q4c/graphs/contributors)

## License
Copyright (c) 2024 by the tamingj/q4c maintainers.

[Apache License, Version 2.0](./license.txt)
