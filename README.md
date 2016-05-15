Carrot
======

Carrot is a templating library for Java that is similar to the Jinja library from python. Jinja -> Ginger -> Carrot, geddit?


It was originally a fork of http://jangod.googlecode.com/svn/trunk/, but that project appears to be abandoned, so I've renamed it and ressurected it.

Getting Started
===============

With Maven:

    <dependency>
      <groupId>au.com.codeka</groupId>
      <artifactId>carrot</artifactId>
      <version>1.0.0</version>
    </dependency>

With Gradle:

    compile 'au.com.codeka:carrot:1.0.0'

First, you need to create a `TemplateEngine`, which will hold the environment for parsing templates and processing them:

    TemplateEngine engine = new TemplateEngine();
    Configuration config = engine.getConfiguration();
    config.setResourceLocator(new FileResourceLocator(config, "path/to/templates"));

Typically, you'll have a "skeleton" template and a "body" template, where the skeleton defines the overall HTML structure that all your pages share, and the body template is the custom things just for that page. The way you do this is by having your body template extend the skeleton template, like so:

skeleton.html:

    <!DOCTYPE html>
    <html>
      <head>
        <title>{% block title %}</title>
      </head>
      <body>
        {% block content %}
      </body>
    </html>

index.html:

    {% extends "skeleton.html" %}
    {% block title %}Hello World{% endblock %}
    {% block content %}
      <h1>Hello, World!</h1>
    {% endblock %}

Finally to process a template, you use the `process` method:

    Map<String, Object> bindings = new TreeMap<>();
    System.out.println(engine.process("index.html", bindings));

Now how do you actually pass data from your application to the template? That's what the bindings are for. Say you have the following in your template:

    <p>Hello, {%= name %}!</p>

You'd pass data to that via a binding, like so:

    Map<String, Object> bindings = new TreeMap<>();
    bindings.put("name", "Dean");
    System.out.println(engine.process("index.html", bindings));

Documentation
=============

The documentation is currently pretty sparse, but you can head over to [codeka.github.io/carrot](http://codeka.github.io/carrot/) to view what there is. Please do not hesitate to [open an issue](https://github.com/codeka/carrot/issues/new) if you have any questions.
