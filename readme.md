# q4c - queries for collections

## Installation
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

## Contributors
[![Contributors](https://contrib.rocks/image?repo=tamingj/q4c)](https://github.com/tamingj/q4c/graphs/contributors)

## License
Copyright (c) 2024 by the tamingj/q4c maintainers.

[Apache License, Version 2.0](./license.txt)
