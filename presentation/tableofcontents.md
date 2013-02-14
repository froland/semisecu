% Web Security

Psycho-sociological aspects
===========================

## Security concerns
For a long time security has been a concern to human being. Has an
increasing part of our life take place on the web, web-security
becomes more and more important.

Security concern apply to various aspect of our internet life. 
- Personal (mail, Facebook account, ...)
- Financial (Online banking)
- Fiscal (Tax on web)
...

## Sentiment of security

To operate successfully a web site must earn the confidence of its
users. You need some degree of trust to give away pieces of personnal
information. But the sentiment of security doesn't always relate to
the actual situation: millions of gamers did entrust a well known
company (sony) with their credit card
number... [PSN outage](http://en.wikipedia.org/wiki/PlayStation_Network_outage). 
So you have to gain some level of trust from your user but not to lure
them in a false sense of security.

## Web payment

Web payment is the application were security concerns are the bigger
for both user and companies that are offering the service as the costs
of mismanagement are evident and can be huge.

## Users as a security flaw

The fact that users are human must be taken into accounts when
designing a security mechanism. Most people are not able or willing to
remember different secure passwords (and lets not speak about a truly
randowm 2048 bits key) for each service they use. 

More over new way of using user to get into secure systems, such as
various form of social engeneering have becomes more and more
widespread over recent year.

## Trade-off: security vs usability

From the previos points, it comes that a good security system must
take the user into account so that he doesn't jeopardize the whole
scheme for the sake of convenience: e.g a "to difficult to remember
password" written on a post-it on the screen.

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

Preventing security flaws is something that has to be done during the
whole life of the application.

### Architecture

[Owasp cheat sheet](https://www.owasp.org/index.php/Application_Security_Architecture_Cheat_Sheet)

[MSDN recomandation](http://msdn.microsoft.com/en-us/library/ff648650.aspx)

>    - Integrate a security review into your architecture design process. Start early on, and as your design changes, review those changes with the steps given in this chapter.
>    - Evolve your security review. This chapter provides questions that you can ask to improve the security of your design. To complete the review process, you might also need to add specific questions that are unique to your application.
>    - Know the threats you are reviewing against. Chapter 2, "Threats and Countermeasures," lists the threats that affect the various components and layers that make up your application. Knowing these threats is essential to improving the results of your review process.


There are no miracle receipts when it comes to designing a secure
application. Altough there are some guidelines which can help you, you
have to ask youself the good questions and mke decisions on a case by
case basis.

### Development

[Owasp cheat sheet coding ](https://www.owasp.org/index.php/Secure_Coding_Cheat_Sheet)
There is a list of best practice that can help to reduce risks.

[Owasp cheat sheet development cycle ](https://www.owasp.org/index.php/Secure_SDLC_Cheat_Sheet)
In development cycles time should be set aside for codereview,
pentesting, ...

### Maintenance

When vulnerabilities have been discovered they have to be patched but
that isn't always possible but solution exists.
[Virtual patching](https://www.owasp.org/index.php/Virtual_Patching_Best_Practices)

Moreover care has to be taken when fixing a bug in a hurry not to open
new security breach.

## Security in application servers


TODO: je n'ai pas bcp d'idées ... (Vincent)
Apllication server offer various service that can help you to design
secured web application...

## E-mail

Email is one of the most used application. It's plagued by spam and
spoofing. Some defensive measure have been taken, most notably:

- SPF [Wikipedia SPF](http://en.wikipedia.org/wiki/Sender_Policy_Framework)
- domain keys [Wikipedia DKIM](http://en.wikipedia.org/wiki/DomainKeys_Identified_Mail)

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

