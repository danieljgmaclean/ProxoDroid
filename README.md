# ProxoDroid

## Overview

A prototype Android application built using Android Studio, Otto and the Estimote SDK. It's purpose was to demonstrate working with Estimote Beacons, creating events and publishing those events to a local CouchDB.

It currently doesn't do much. It will attempt to connect to a local CouchDB assuming certain configuration is in place. It will then send events in JSON messages to CouchDB via it's RESTful HTTPS API for when it's in range of a specific Beacon.

## TODO's

- Initialise configured CouchDB if not previously done
- Implement Leaving Message event
- Make events configurable based on Beacons via UI

## RubySpeaker

A seperate application to react to events published to CouchDB. Check it out here.
