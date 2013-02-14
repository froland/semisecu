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

## Threat agents

Who are the threat agents?

- External threats
    - internet users
    - viruses
    - organized crime
- Internal threats
    - employees
    - intranet users
    - administrators
- Natural threats
    - disasters
    - hazards

*Why* agents attack?

- Gain: financial, information, competition
- To show off
- By accident (unintentional)

Threat agent have different skills, motivation (how much do they win by
performing the attack).

## Risks and ranking

[Wikipedia definition](http://en.wikipedia.org/wiki/IT_risk):

> IT risk: the potential that a given threat will exploit vulnerabilities of
> an asset or group of assets and thereby cause harm to the organization. It
> is measured in terms of a combination of the probability of occurrence of an
> event and its consequence.

	risk = likelihood * impact

- Likelihood: how probable is it that the attack will be successful
- Impact: how much damage the attack will cause

Likelihood is influenced by threat agent and vulnerability factors.
The vulnerability factors are: the ease of discovery, ease of exploit,
awareness, intrusion detection.
The impact factors are: loss of confidentiality, integrity, availability or
accountability; financial or reputation damage or privacy violation.

## Context of web applications

### Network

Web applications are usually available on the internet. This means that anyone
can access the application. This is why authentication is an important subject
and will be the subject of the next chapter.

### Points of failure

A web application can be attacked at multiple levels: the client can be
infected, the application server could be down, the database servers could be
overloaded or simply a connection failure can occur.

## OWASP - top 10

Describe all 10 attacks.
Demo for available ones.

1. Injection
2. XSS
3. Broken Authentication and Session Management
4. Insecure Direct Object References
5. CSRF
6. Security Misconfiguration
7. Insecure Cryptographic Storage
8. Failure to Restrict URL Access
9. Insufficient Transport Layer Protection
10. Unvalidated Redirects and Forwards

For each attack scenario:

- Principle:
    - Attack vectors
    - Security weakness
    - Impacts
    - Exploitability, prevalence, detectability, impact
- Attack-scenario-like description
    - Attack description
    - Vulnerable code
    - Preventing the attack

Links:

- [OWASP](https://www.owasp.org/)
- [Risk rating](https://www.owasp.org/index.php/OWASP_Risk_Rating_Methodology)

## DoS

[Denial of Service](http://en.wikipedia.org/wiki/Denial-of-service_attack) is
an attack that aims to bring down (or make unavailable) some attacked service
usually by causing server overload.

Types:

- SYN flood
- Application level flood
- R-U-Dead-Yet (never-ending POST transmissions; ex:
  [Slowloris](http://ha.ckers.org/slowloris/))
- Slow read attack
- DDos
- Unintentional DoS: a page suddenly becomes popular.
  Examples: a page linked from slashdot, "Joueur du Grenier", etc.
- ...

Protection: firewall, IPS (Intrusion Prevention Systems)

## MitM

[Man in the Middle](http://en.wikipedia.org/wiki/Man-in-the-middle_attack) in
an attack where the attacker plays the role of the server to the client and
the role of the client to the server. All the communication transits through
the attacker who can modify messages as they are sent.

[Description on OWASP](https://www.owasp.org/index.php/Man-in-the-middle_attack)

Vulnerable on public WiFi networks.

Examples: the tool [ettercap](http://ettercap.github.com/ettercap/), backdoors
on routers

Protection: cryptography, PKI (Public Key Infrastructures)

## Social engineering

[Social engineering](http://en.wikipedia.org/wiki/Social_engineering_%28security%29)
consists in manipulating people so they divulge confidential information.
Similar to fraud.

Examples include pretexting, phishing (see later), quid pro quo.

> In a 2003 information security survey, 90% of office workers gave
> researchers what they claimed was their password in answer to a survey
> question in exchange for a cheap pen.

Protection: education, framework of trust

### Phishing

[Phishing](http://en.wikipedia.org/wiki/Phishing) is the attempt to acquire
user information through masquerading as a legitimate web site.
Examples include cloned websites, link manipulation, phone phishing, etc.

Protection: education, help to identify legitimate web sites

## Other attacks

- brute force
- sequence prediction
- path traversal
- failure to restrict automation

As technology evolves, new forms of attacks appear.
Instead of trying to fix vulnerabilities, focus on establishing strong
security controls.

## Common points

Many security weaknesses are due to improper input validation, missing
authorization checks, misconfiguration, other assumptions, etc.
Let's not forget the education factor that plays an important role in social
engineering.

Authentication and identity
===========================

Authentication vs identity
Why authentication?

TODO add at least definitions

## Types

- Passwords: the most common form of authentication, must have a
  sufficient complexity and easy to change periodically.
  See [OWASP Authentication Cheat Sheet](http://owasp.com/index.php/Authentication_Cheat_Sheet).
  TODO add XKCD?

- OTP: One time passwords are used to avoid replay attacks. Need an advanced
  delivery method.

- Certificates: A trusted authority issues certificates to confirm the ID
  of something. Used in SSL/TLS.
  - 1-way: Only one participant is authenticated (at least using this
    mean), usually the server in webapps.
  - 2-way: Both participant are authenticated by certificates

- 2-factor: You should use two different factors of
  authentication. Those factor are what the authenticated entity is
  (biometrics...), what it owns (a bank card, ...) or what he knows (a
  password, a pin code, ...)

- Token "Something which ownership gives a form of identification"

Most commons are smart card and digipass

## Lost password

Password recovery: a usability vs security trade-off.
How to do it properly?

TODO explain more

## Signature challenge

An authentication based upon "answering" a question about a secret
known by the participants. For example, when I encrypt something (a nonce)
with my correspondent's public key and ask him for the decrypted and encrypted
message.

## SSO

- Principle
- Pro
    - Easier for the user
    - Not trivial to build a secure authentication scheme
    - If they have only one password users tend to treat it with more care
- Cons
    - All your eggs in the same basket (impact if compromised)
    - You are dependant upon your provider (confidence, availability, ...)

## Kerberos

[Kerberos](http://web.mit.edu/kerberos/) is an example of tickets based SSO.

- origin
- How it works

## Provider extensions

Common SSO used over the web:

- Google
- Facebook: using
  [Facebook Connect API](http://developers.facebook.com/docs/howtos/login/server-side-login/)
- [OpenID](https://openid.net/)

These organisations provide single sign-on to other websites for free
using a dedicated API which is much simpler than devising your own
sign-on mechanism.

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

WebScarab

## Honeypot

## NIDS

Example: snort

## Post-mortem analysis

## Noise as a diversion

## Load balancing

## Performances

References
==========

- [Police fédérale](http://www.polfed-fedpol.be/)
- Chronique RTBF

- [XKCD: Security](http://xkcd.com/538/)
- [XKCD: Password Strength](http://xkcd.com/936/)
- [XKCD: Password Reuse](http://xkcd.com/792/)

