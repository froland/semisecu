% Web Security

Psycho-sociological aspects
===========================

## Security concerns

## Sentiment of security

## Web payment

## Users as a security flaw

## Trade-off: security vs usability

Attacks
=======

## Attackers

- origin
- motivation

## Risks and ranking

risk = vulnerability * threat * impact

## Context of web applications

- network
- points of failure

## OWASP - top 10

Describe all 10 attacks.
Demo for available ones.

## DoS

## MitM

- WiFi
- backdoor

## Phishing

## Social engineering

## Other attacks

## Common points

Authentication and identity
===========================

Authentication vs identity
Why authentication?


## Types

- passwords: the most common form of authentication, must have a
  sufficient complexity and easy to change periodically... see owasp
  cheat sheet
  
- OTP: One time password try avoiding replay attacks. Needs an advanced
  delivery method.
  
- Certificates A trusted authoritie issues certificates to confirm ID
  of something. Used in SSL/TLS.
  - 1-way Only one participant is authenticated (at least using this
    mean) usually the server in webapps.
  - 2-way Both participant are autheenticated by certificates


- 2-factor You should use two different factor of
  authenticatation. Those factor are what the authentcaed entity is
  (biometrics...), what it owns (a bank card, ...) or what he knows (a
  password, a pin code, ...)

- Token "Something which ownership gives a form of identification"

Most commons are smart card and digipass

## Lost password

Password recovery a usability vs security trade-off. 
How to do it properly?

## Signature challenge

An authentication based upon "answering" a question about a secret
known by the participants. eg I encript something (a nonce) with my
correspondant public key and ask him for the decript.

## SSO
-Principle
-Pro
Easier for the user
Not trivial tu build a secure authentication scheme
If they have only one password users tend to treat it with more care


-Cons
All your eggs in the same basket
You are dependant upon your provider (confidence, availability, ...)
Impact if compromised



## Kerberos: An example of tickets based SSO
- origin
- How it works

## Provider extension: Common SSO used over the web
- Google
- Facebook
- OpenID
Those organisation provides single sign on to other website for free
using a dedicated API which is much simpler than devising your own
sign on mechanism

Prevention
==========

## Different moments

### Architecture

### Development

### Maintenance

## Security in application servers

## E-mail

- SPF
- domain keys

## Load balancing

## Application firewall

## HTTPS

## Browser restrictions

(anti-XSS, etc.)

## Variety of environments

- browser
- OS
- server

Detection and analysis
======================

## Penetration testing

## Honey pot

## NIDS

## Post-mortem analysis

## Noise as a diversion

## Load balancing

## Performances

References
==========

- [Police fédérale](http://www.polfed-fedpol.be/)
- [OWASP Authentication Cheat Sheet](http://owasp.com/index.php/Authentication_Cheat_Sheet)
- [Kerberos Website](http://web.mit.edu/kerberos/)
- [Facebook Connect API](http://developers.facebook.com/docs/howtos/login/server-side-login/)
- [OpenID](https://openid.net/)

- XKCD 538 : security
- http://xkcd.com/936/
- http://xkcd.com/792/
- Chronique RTBF

