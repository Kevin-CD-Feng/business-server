#git branch=master
DOCKER=docker
IMG_PATH=registry.xingtu.com:8088/openvone
NAMESPACE=biz-service
VERSION=V6.0.4
PULL=git pull origin
BUILD_DATE = $(shell date +"%Y%m%d")
BUILD_INDEX = 1


recognition-prepare-server:
	$(DOCKER) rmi -f $(IMG_PATH)/$(NAMESPACE)/$@:$(VERSION)
	$(DOCKER) build -t $(IMG_PATH)/$(NAMESPACE)/$@:$(VERSION).$(BUILD_DATE) -f ./Dockerfile .
	$(DOCKER) push $(IMG_PATH)/$(NAMESPACE)/$@:$(VERSION).$(BUILD_DATE)
	$(DOCKER) save $(IMG_PATH)/$(NAMESPACE)/$@:$(VERSION).$(BUILD_DATE)|gzip>$@_$(VERSION)_$(BUILD_DATE).tar.gz

pull:
	$(PULL)

all: \
	pull \
	recognition-prepare-server