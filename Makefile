PATH := ./work/redis-git/src:${PATH}
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))
STUNNEL_BIN := $(shell which stunnel)
BREW_BIN := $(shell which brew)
YUM_BIN := $(shell which yum)
APT_BIN := $(shell which apt-get)



define REDIS_CLUSTER_CONFIG1
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:7380 master - 1434887920102 1434887920002 0 connected 12000-16383
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:7379 myself,master - 0 0 1 connected 0-11999
vars currentEpoch 3 lastVoteEpoch 0
endef

define REDIS_CLUSTER_CONFIG2
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:7379 master - 1434887920102 1434887920002 1 connected 0-11999
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:7380 myself,master - 0 0 0 connected 12000-16383
vars currentEpoch 3 lastVoteEpoch 0
endef



#######
# Redis
#######
.PRECIOUS: work/redis-%.conf

# Sentinel monitored slave
work/redis-6380.conf:
	@mkdir -p $(@D)

	@echo port 6380 >> $@
	@echo daemonize yes >> $@
	@echo pidfile $(shell pwd)/work/redis-6380.pid >> $@
	@echo logfile $(shell pwd)/work/redis-6380.log >> $@
	@echo save \"\" >> $@
	@echo appendonly no >> $@
	@echo client-output-buffer-limit pubsub 256k 128k 5 >> $@
	@echo unixsocket $(ROOT_DIR)/work/socket-6380 >> $@
	@echo unixsocketperm 777 >> $@
	@echo slaveof 127.0.0.1 6379 >> $@

work/redis-%.conf:
	@mkdir -p $(@D)

	@echo port $* >> $@
	@echo daemonize yes >> $@
	@echo pidfile $(shell pwd)/work/redis-$*.pid >> $@
	@echo logfile $(shell pwd)/work/redis-$*.log >> $@
	@echo save \"\" >> $@
	@echo appendonly no >> $@
	@echo client-output-buffer-limit pubsub 256k 128k 5 >> $@
	@echo unixsocket $(ROOT_DIR)/work/socket-$* >> $@
	@echo unixsocketperm 777 >> $@

work/redis-%.pid: work/redis-%.conf work/redis-git/src/redis-server
	work/redis-git/src/redis-server $<

redis-start: work/redis-6379.pid work/redis-6380.pid

##########
# Sentinel
##########
.PRECIOUS: work/sentinel-%.conf

work/sentinel-%.conf:
	@mkdir -p $(@D)

	@echo port $* >> $@
	@echo daemonize yes >> $@
	@echo pidfile $(shell pwd)/work/redis-sentinel-$*.pid >> $@
	@echo logfile $(shell pwd)/work/redis-sentinel-$*.log >> $@

	@echo sentinel monitor mymaster 127.0.0.1 6379 1 >> $@
	@echo sentinel down-after-milliseconds mymaster 100 >> $@
	@echo sentinel failover-timeout mymaster 100 >> $@
	@echo sentinel parallel-syncs mymaster 1 >> $@
	@echo unixsocket $(ROOT_DIR)/work/socket-$* >> $@
	@echo unixsocketperm 777 >> $@

work/sentinel-%.pid: work/sentinel-%.conf work/redis-git/src/redis-server
	work/redis-git/src/redis-server $< --sentinel
	sleep 0.5

sentinel-start: work/sentinel-26379.pid work/sentinel-26380.pid work/sentinel-26381.pid

##########
# Cluster
##########
.PRECIOUS: work/cluster-node-%.conf

work/cluster-node-7385.conf:
	@mkdir -p $(@D)

	@echo port 7385 >> $@
	@echo daemonize yes >> $@
	@echo pidfile $(shell pwd)/work/cluster-node-7385.pid >> $@
	@echo logfile $(shell pwd)/work/cluster-node-7385.log >> $@
	@echo save \"\" >> $@
	@echo appendonly no >> $@
	@echo unixsocket $(ROOT_DIR)/work/socket-7385 >> $@
	@echo cluster-enabled yes >> $@
	@echo cluster-node-timeout 50 >> $@
	@echo cluster-config-file $(shell pwd)/work/cluster-node-config-7385.conf >> $@
	@echo requirepass foobared >> $@


work/cluster-node-7479.conf:
	@mkdir -p $(@D)

	@echo port 7479 >> $@
	@echo daemonize yes >> $@
	@echo pidfile $(shell pwd)/work/cluster-node-7479.pid >> $@
	@echo logfile $(shell pwd)/work/cluster-node-7479.log >> $@
	@echo save \"\" >> $@
	@echo appendonly no >> $@
	@echo cluster-enabled yes >> $@
	@echo cluster-node-timeout 50 >> $@
	@echo cluster-config-file $(shell pwd)/work/cluster-node-config-7479.conf >> $@
	@echo cluster-announce-port 7443 >> $@
	@echo requirepass foobared >> $@


work/cluster-node-7480.conf:
	@mkdir -p $(@D)

	@echo port 7480 >> $@
	@echo daemonize yes >> $@
	@echo pidfile $(shell pwd)/work/cluster-node-7480.pid >> $@
	@echo logfile $(shell pwd)/work/cluster-node-7480.log >> $@
	@echo save \"\" >> $@
	@echo appendonly no >> $@
	@echo cluster-enabled yes >> $@
	@echo cluster-node-timeout 50 >> $@
	@echo cluster-config-file $(shell pwd)/work/cluster-node-config-7480.conf >> $@
	@echo cluster-announce-port 7444 >> $@
	@echo requirepass foobared >> $@


work/cluster-node-%.conf:
	@mkdir -p $(@D)

	@echo port $* >> $@
	@echo daemonize yes >> $@
	@echo pidfile $(shell pwd)/work/cluster-node-$*.pid >> $@
	@echo logfile $(shell pwd)/work/cluster-node-$*.log >> $@
	@echo save \"\" >> $@
	@echo appendonly no >> $@
	@echo client-output-buffer-limit pubsub 256k 128k 5 >> $@
	@echo unixsocket $(ROOT_DIR)/work/socket-$* >> $@
	@echo cluster-enabled yes >> $@
	@echo cluster-node-timeout 50 >> $@
	@echo cluster-config-file $(shell pwd)/work/cluster-node-config-$*.conf >> $@


work/cluster-node-%.pid: work/cluster-node-%.conf work/redis-git/src/redis-server
	work/redis-git/src/redis-server $<

cluster-start: work/cluster-node-7379.pid work/cluster-node-7380.pid

##########
# stunnel
##########

work/stunnel.conf:
	@mkdir -p $(@D)

	@echo cert=$(ROOT_DIR)/work/cert.pem >> $@
	@echo key=$(ROOT_DIR)/work/key.pem >> $@
	@echo capath=$(ROOT_DIR)/work/cert.pem >> $@
	@echo cafile=$(ROOT_DIR)/work/cert.pem >> $@
	@echo delay=yes >> $@
	@echo pid=$(ROOT_DIR)/work/stunnel.pid >> $@
	@echo foreground = no >> $@

	@echo [stunnel] >> $@
	@echo accept = 127.0.0.1:6443 >> $@
	@echo connect = 127.0.0.1:6479 >> $@
	
work/stunnel.pid: work/stunnel.conf ssl-keys
	which stunnel4 >/dev/null 2>&1 && stunnel4 $(ROOT_DIR)/work/stunnel.conf || stunnel $(ROOT_DIR)/work/stunnel.conf

stunnel-start: work/stunnel.pid

export REDIS_CLUSTER_CONFIG1
export REDIS_CLUSTER_CONFIG2

start: cleanup
	@echo "$$REDIS_CLUSTER_CONFIG1" > work/cluster-node-config-7379.conf
	@echo "$$REDIS_CLUSTER_CONFIG2" > work/cluster-node-config-7380.conf
	$(MAKE) redis-start
	$(MAKE) sentinel-start
	$(MAKE) cluster-start
	$(MAKE) stunnel-start


cleanup: stop
	@mkdir -p work
	rm -f work/cluster-node*.conf 2>/dev/null
	rm -f work/*.rdb work/*.aof work/*.conf work/*.log 2>/dev/null
	rm -f *.aof
	rm -f *.rdb
	rm -f work/socket-*

##########
# SSL Keys
#  - remove Java keystore as becomes stale
##########
work/key.pem work/cert.pem:
	@mkdir -p $(@D)
	openssl genrsa -out work/key.pem 4096
	openssl req -new -x509 -key work/key.pem -out work/cert.pem -days 365 -subj "/O=lettuce/ST=Some-State/C=DE/CN=lettuce-test"
	chmod go-rwx work/key.pem
	chmod go-rwx work/cert.pem
	- rm -f work/keystore.jks

work/keystore.jks:
	@mkdir -p $(@D)
	$$JAVA_HOME/bin/keytool -importcert -keystore work/keystore.jks -file work/cert.pem -noprompt -storepass changeit

ssl-keys: work/key.pem work/cert.pem work/keystore.jks

stop:
	pkill stunnel || true
	pkill redis-server && sleep 1 || true
	pkill redis-sentinel && sleep 1 || true

test-coveralls: start
	mvn -B -DskipTests=false clean compile test jacoco:report coveralls:report
	$(MAKE) stop

test: start
	mvn -B -DskipTests=false clean compile test
	$(MAKE) stop

prepare: stop

ifndef STUNNEL_BIN
ifeq ($(shell uname -s),Linux)
ifdef APT_BIN
	sudo apt-get install -y stunnel
else

ifdef YUM_BIN
	sudo yum install stunnel
else
	@@echo "Cannot install stunnel using yum/apt-get"
	@exit 1
endif

endif

endif

ifeq ($(shell uname -s),Darwin)

ifndef BREW_BIN
	@@echo "Cannot install stunnel because missing brew.sh"
	@exit 1
endif

	brew install stunnel

endif

endif

work/redis-git/src/redis-cli work/redis-git/src/redis-server:
	[ ! -e work/redis-git ] && git clone https://github.com/antirez/redis.git --branch unstable --single-branch work/redis-git && cd work/redis-git|| true
	[ -e work/redis-git ] && cd work/redis-git && git fetch && git merge origin/master || true
	$(MAKE) -C work/redis-git clean
	$(MAKE) -C work/redis-git -j4

clean:
	rm -Rf work/
	rm -Rf target/
