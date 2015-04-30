# ProxoDroid

![Build Status](https://travis-ci.org/danieljgmaclean/ProxoDroid.svg)

## Overview

A prototype Android application built using Android Studio, [Otto](https://github.com/square/otto) and the [Estimote SDK](https://github.com/Estimote/Android-SDK). It's purpose was to demonstrate working with Estimote Beacons, creating events and publishing those events to a local CouchDB.

It currently doesn't do much. It will attempt to connect to a local CouchDB assuming certain configuration is in place. It will then send events in JSON messages to CouchDB via it's RESTful HTTPS API for when it's in range of a specific Beacon.

## TODO's

- Initialise configured CouchDB if not previously done
- Implement Leaving Message event
- Make events configurable based on Beacons via UI

## RubySpeaker

A seperate application to react to events published to CouchDB. Check it out [here](https://github.com/danieljgmaclean/RubySpeaker).

## Prerequisites

You must do the following to use the application
- Own at least one Estimote Beacon or be able to create a virtual beacon
- Have CouchDB installed on a locally or remotely accessible server
- Be willing to mess around with some code because this app is still in early development
