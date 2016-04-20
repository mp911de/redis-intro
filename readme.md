# Redis Intro

This project is an introduction into Redis. It contains examples using Java libraries to explain Redis usage.

## Bootstrapping Redis

This repository contains a `Makefile` that will make you happy. Your only pre-requisites are:

1. Having build tools like `make`, `gcc` or Xcode and Java installed
2. Having `stunnel` (or `stunnel4`) installed

Then go into your favorite console and start the servers:

```
$ make start
```

This will download the latest Redis version from the unstable branch, build and start it. It will start following Redis instances:

* Redis Standalone configured as Master on port `6379`
* A `stunnel` SSL proxy for the Redis Standalone Master on port `6443`
* Redis Standalone configured as Slave on port `6380`
* 3 Redis Sentinel instances monitoring the Master/Slave setup on ports `26379`, `26380` and `26381`
* 2 Redis Cluster nodes on ports `7379` and `7380`

The commandline interface can be started with

```
$ work/redis-git/src/redis-cli -p 6379
```