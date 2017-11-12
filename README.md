# Star Trek Project 
[![Build Status](https://travis-ci.org/tarikdemirci/startrekproject.svg?branch=master)](https://travis-ci.org/tarikdemirci/startrekproject)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d909c309b7c048ea9456447d98c3fa21)](https://www.codacy.com/app/tarikdemirci/startrekproject?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tarikdemirci/startrekproject&amp;utm_campaign=Badge_Grade)
[![Maintainability](https://api.codeclimate.com/v1/badges/65540663e74e9a071c7c/maintainability)](https://codeclimate.com/github/tarikdemirci/startrekproject/maintainability)
[![codecov](https://codecov.io/gh/tarikdemirci/startrekproject/branch/master/graph/badge.svg)](https://codecov.io/gh/tarikdemirci/startrekproject)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

## What's this?
This is a project to honour Star Trek. It has two main features:
- Translate a name in English to Klingon. Output format is Klingon unicode values in hexadecimal.
- Search name in Star Trek API (Stapi) and print a set of all characters' species.

## Dependencies
- Java 8

## Build
- `./mvnw clean package`

## Run
- `target/startrekproject-executable <name>`
## Examples
```
$ target/startrekproject-executable "Nyota Uhura"
0xF8DB 0xF8E8 0xF8DD 0xF8E3 0xF8D0 0x0020 0xF8E5 0xF8D6 0xF8E5 0xF8E1 0xF8D0
Human
```
```
$ target/startrekproject-executable ed
0xF8D4 0xF8D3
Human Pralor Ventaxian Cardassian Kyrian Bajoran Aldean Vaadwaur Cravic Cairn Pakled Enaran Trill
```

## Project Structure
- I've designed java package structure as `<feature>.<layer>`.
 So there are two top level packages for two features:
  - klingon
  - stapi
- Each top level package has its own subpackages for layers:
  - external
  - service
  - domain
  
### Components
```
                                 +---------------+          +----------------+
                    +----------> | KlingonService| +------> | PlqadMapClient | +------>  EnglishToPlqadMapping.json
                    |            +---------------+          +----------------+
                    |
             +------+----+
User  +----> | CliRunner |
             +-----+-----+
                   |             +---------------+          +---------------+
                   +-----------> |  StapiService | +------> |  StapiClient  | +------->  http://stapi.co/api/v1/rest
                                 +---------------+          +---------------+

```
## Implementation Details
- I've tried to consume Stapi as parallel as possible. 
First I've used Java 8's parallel streams. 
I couldn't reached the massive parallelism I've aimed since it uses its default thread pool. So I've used a trick.
Executing parallel streams as a task in custom ForkJoinPool solves the problem. Check related links section for more details.

## Notes
- Sometimes Stapi takes too long to respond. Default timeout for each stapi connection is 10 secs. You can change it in application.properties
- Stapi has request throttle as 250 API requests for a limited amount of time (half an hour I guess).


## Related Links
- Stapi
  - http://stapi.co/
- Java 8 parallel stream thread pool limitation
  - https://stackoverflow.com/questions/21163108/custom-thread-pool-in-java-8-parallel-stream
