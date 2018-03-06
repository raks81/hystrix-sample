#!/usr/bin/env bash

loadtest -n 100 -c 20 "http://localhost:8080/v2/service?timeout=2000&errors=0&input=1"