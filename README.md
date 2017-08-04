Carrot
======

[![Build Status](https://travis-ci.org/codeka/carrot.svg?branch=master)](https://travis-ci.org/codeka/carrot)
[![Code Coverage](https://img.shields.io/codecov/c/github/codeka/carrot/master.svg)](https://codecov.io/github/codeka/carrot?branch=master)

Carrot is a templating library for Java that is similar to the Jinja library from python. Jinja -> Ginger -> Carrot,
geddit?

It was originally a fork of http://jangod.googlecode.com/, but that project appears to be abandoned, so I've renamed it,
and subsequently (in version 2.x) rewritten it.

Getting Started
===============

With Maven:

    <dependency>
      <groupId>au.com.codeka</groupId>
      <artifactId>carrot</artifactId>
      <version>2.3.0</version>
    </dependency>

With Gradle:

    compile 'au.com.codeka:carrot:2.3.0'

First, you need to create a `CarrotEngine`, which will hold the environment for parsing templates and processing them:

    CarrotEngine engine = new CarrotEngine();
    Configuration config = engine.getConfiguration();
    config.setResourceLocator(new FileResourceLocator(config, "path/to/templates"));

Typically, you'll have a "skeleton" template and a "body" template, where the skeleton defines the overall HTML
structure that all your pages share, and the body template is the custom things just for that page. The way you do this
is by having your body template extend the skeleton template, like so:

skeleton.html:

    <!DOCTYPE html>
    <html>
      <head>
        <title>{% block "title" %}{% end %}</title>
      </head>
      <body>
        {% block "content" %}{% end %}
      </body>
    </html>

index.html:

    {% extends "skeleton.html" %}
    {% block "title" %}Hello World{% end %}
    {% block "content" %}
      <h1>Hello, World!</h1>
    {% end %}

Finally to process a template, you use the `process` method:

    System.out.println(engine.process("index.html", new EmptyBindings()));

Now how do you actually pass data from your application to the template? That's what the bindings are for. Say you have
the following in your template:

    <p>Hello, {{ name }}!</p>

You'd pass data to that via `Bindings`, like so:

    Map<String, Object> bindings = new TreeMap<>();
    bindings.put("name", "Dean");
    System.out.println(engine.process("index.html", new MapBindings(bindings)));

Documentation
=============

The documentation is currently pretty sparse, but you can head over to
[codeka.github.io/carrot](http://codeka.github.io/carrot/) to view what there is. Please do not hesitate to
[open an issue](https://github.com/codeka/carrot/issues/new) if you have any questions.
