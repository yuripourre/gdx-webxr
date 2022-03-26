# gdx-webxr
A WebXR implementation for libGDX

# Getting started

## Compatibility

* gdx-webxr requires libGDX 1.10.1+

## Deploy Locally

To deploy it locally, simple run:

```
mvn install
```


## Install

Add dependency in your core project :

```
project(":core") {
    dependencies {
    	...
        api "com.harium.gdx:webxr:$webxrVersion"
    }
}
```

For GWT (html) projects you need to add source dependency and inherit GWT module in your core .gwt.xml file.

```
project(":html") {
    dependencies {
    	...
        api "com.harium.gdx:webxr:$webxrVersion:sources"
    }
}
```

```
<module>
	<inherits name='WebXR' />
	...
</module>
```
