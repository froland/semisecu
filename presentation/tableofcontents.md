% Web Security

Psycho-sociological aspects
===========================

## Security concerns

For a long time security has been a concern to human being. It has an
increasing part of our life take place on the web, web-security
becomes more and more important.

Security concern apply to various aspect of our internet life.

- Personal (mail, Facebook account, ...)
- Financial (Online banking)
- Fiscal (Tax on web)
- ...

## Sentiment of security

To operate successfully a web site must earn the confidence of its
users. You need some degree of trust to give away pieces of personal
information. But the sentiment of security doesn't always relate to
the actual situation: millions of gamers did entrust a well known
company (Sony) with their credit card number...
[PSN outage](http://en.wikipedia.org/wiki/PlayStation_Network_outage).
So you have to gain some level of trust from your user but not to lure
them in a false sense of security.
For example, some users might think that an HTTPS web site is secured against
all attacks; however, HTTPS prevents only some of the attacks there are.

## Web payment

Web payment is the application were security concerns are the bigger
for both user and companies that are offering the service as the costs
of mismanagement are evident and can be huge.

## Users as a security flaw

The fact that users are human must be taken into accounts when
designing a security mechanism. Most people are not able or willing to
remember different secure passwords (and let's not speak about a truly
random 2048 bits key) for each service they use.

Moreover, new ways of using the user to get into secure systems are
widespread. Such as various form of social engineering.
For [example](http://www.theregister.co.uk/2003/04/18/office_workers_give_away_passwords/),
90% of people gave away their passwords in exchange for a pen.

## Trade-off: security vs usability

From the previous points, it comes that a good security system must
take the user into account so that they don't jeopardize the whole
scheme for the sake of convenience.
For example: too difficult to remember passwords written on a post-it, too
many security measures dissuade the user from using the application (or at
least use it as little as possible).

Attacks
=======

## Attack trees

[Attack trees](http://en.wikipedia.org/wiki/Attack_tree) are a model
describing by what means an asset can be attacked. They are used to determine
and understand threats that may arise.

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
- Natural threats (disasters)
- Technical failures
    - loss of essential services (example: no electricity)
    - hardware failure
    - software failure

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
  Examples: anonymous attack, etc.
- Unintentional DoS: a page suddenly becomes popular.
  Examples: a page linked from slashdot, "Joueur du Grenier", etc.
- ...

Protection: firewall, IPS (Intrusion Prevention Systems), black-holing

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

Identity: The identity of somebody/something is who/what he/it is.
Authentication: The authentication is the act aff asserting the
identity of something/someone.

Why authentication?
Authentication is needed when you want to transmit confidential
information, you want to be sure that your correspondant isn't
impersonated by somebody else.

## Types

- Passwords: the most common form of authentication, must have a
  sufficient complexity and easy to change periodically.
  See [OWASP Authentication Cheat Sheet](http://owasp.com/index.php/Authentication_Cheat_Sheet).
  See also XKCD password strength.

- OTP: One time passwords are used to avoid replay attacks. Need an advanced
  delivery method.

- Certificates: A trusted authority issues certificates to confirm the ID
  of something. Used in SSL/TLS.
  - 1-way: Only one participant is authenticated (at least using this
    mean), usually the server in webapps.
  - 2-way: Both participants are authenticated by certificates

- 2-factor: You should use two different factors of
  authentication. Those factor are what the authenticated entity is
  (biometrics, ...), what it owns (a bank card, ...) or what he knows (a
  password, a pin code, ...)

- Token "Something which ownership gives a form of identification"

Most commons are smart card and digipass

## Lost password

Password recovery: a usability vs security trade-off.

How to do it properly?
- Use of security question or other strong authentication
- send a token over a side channel
- allow the user to change password
- confirm change
[Owasp cheat sheet](http://owasp.com/index.php/Forgot_Password_Cheat_Sheet)

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

Kerberos is a SSO developped at MIT to solve the problem of allowing
some users to use restricted ressources. MIT provide a free
implementation of the protocol but it's also found in many commercial
products.

How it works.
You authenticate to an authentication server, it gives you a signed
ticket.

With this general ticket you ask the ticket gateway system a ticket
which allows you to get the particular service you need from a
particuliar host.

You give this second ticket to the host that will then perform the
service for you.

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

- [OWASP cheat sheet](https://www.owasp.org/index.php/Application_Security_Architecture_Cheat_Sheet)

- [MSDN recommendation](http://msdn.microsoft.com/en-us/library/ff648650.aspx)

  > - Integrate a security review into your architecture design process. Start
  >   early on, and as your design changes, review those changes with the
  >   steps given in this chapter.
  > - Evolve your security review. This chapter provides questions that you
  >   can ask to improve the security of your design. To complete the review
  >   process, you might also need to add specific questions that are unique
  >   to your application.
  > - Know the threats you are reviewing against. Chapter 2, "Threats and
  >   Countermeasures," lists the threats that affect the various components
  >   and layers that make up your application. Knowing these threats is
  >   essential to improving the results of your review process.

There are no miracle receipts when it comes to designing a secure
application. Although there are some guidelines which can help you. You
have to ask yourself the good questions and make decisions on a case by
case basis.

### Development

- [OWASP cheat sheet coding](https://www.owasp.org/index.php/Secure_Coding_Cheat_Sheet)
  There is a list of best practice that can help to reduce risks.
- [OWASP cheat sheet development cycle](https://www.owasp.org/index.php/Secure_SDLC_Cheat_Sheet)
  In development cycles time should be set aside for code review, pentesting,
  etc.
- Secure configuration during deployment

### Maintenance

When vulnerabilities have been discovered they have to be patched as soon as
possible.
[Virtual patching](https://www.owasp.org/index.php/Virtual_Patching_Best_Practices)
is a security policy layer that prevents the exploitation of a known
vulnerability until a patch is released.

Moreover, care has to be taken when fixing a bug in a hurry not to open
new security breaches.

## Using frameworks

You do not develop the first web application. Frameworks already exists to
ease the development of your application while providing several security
mechanisms.
Often a framework will come with authentication support, escaping of values,
input validation, etc.

Examples of frameworks: [Spring](http://www.springsource.org/),
[CakePHP](http://cakephp.org/), etc.

## Security in application servers

Application servers offer various services that can help you to configure
secured web applications.

Authentication management is generally part of the application server,
directory listing can be enabled or disabled, user session management (ex:
`JSESSIONID`), error-handling, input encoding, etc.

## E-mail

Email is one of the most used application. It's plagued by spam and
spoofing. Some defensive measure have been taken, most notably:

- [SPF](http://en.wikipedia.org/wiki/Sender_Policy_Framework)
- Domain keys: [Wikipedia DKIM](http://en.wikipedia.org/wiki/DomainKeys_Identified_Mail)

## Load balancing

When a single host cannot handle all the requests, we can dispatch these
request to multiple servers running the same application. This is the job of a
load balancer. It will forward requests to some destination server based on
some rule.

Load balancers may provide features such as
[SYN cookies](http://en.wikipedia.org/wiki/SYN_cookies) to mitigate DoS
attacks. Also, load balancers are often used to implement failover: when a
particular server goes down, it can redirect requests to another server.
Besides this, they can offload some treatment from the web server, such as
HTTP compression, SSL, caching, etc.
Moreover, the internal network is hidden behind the load balancer.

## Application firewall

Application firewall are filters which inspects the traffic that comes
from and goes to your application and is able to block inappropriate
content.
[OWASP application firewall](https://www.owasp.org/index.php/Web_Application_Firewall).

They provide an additional layer of protection, by example, against
SQL injection by scanning HTML form for SQL content.

Can be used to patch security holes of applications which aren't modifiable
for some reason (virtual patching).

## HTTPS

HTTP over SSL/TLS allow to encrypt and authenticate HTTP
connection. Provides protection against eavesdropping and spoofing but
HTTPS doesn't equal with security. It's a part of a solution.

## Browser restrictions

Cross-domain XHR: the browsers disallow contacting other websites
through the use of JavaScript. A website can ask for the browser to
lift selectively this restriction via header `Access-Control`.
Some browsers allow to block (selectively) the execution of Flash and/or
JavaScript to protect the user against malicious code hosted by those sites.

There is also often a "private navigation" mode which prevents the
browser from storing data.
In modern browsers tabs are sandboxed in order to prevent one tab from
accessing data used by others.

Browsers also validate SSL certificates and prompt users when a
dubious certificate is found.
Some of them also provide blacklisting of known rogue sites and
correct misspelled addresses to protect users from phishing.

## Variety of environments

The variety of environments available to the users leads to
difficulties when it comes to test possible setups for
vulnerabilities. Moreover some browsers (e.g. IE6) require workarounds to
display pages correctly which extend the amount of code and thus
increase vulnerability likeliness.

Detection and analysis
======================

## Penetration testing

Penetration testing (or pentesting) is the action of attacking a system with
the consent of the owner in the goal of finding security holes.
An example of a framework/applications for pentesting are:

- [Metasploit](http://www.metasploit.com/about/penetration-testing-basics/)
- [Nessus](http://www.tenable.com/products/nessus)
- [WebScarab](https://www.owasp.org/index.php/Webscarab)
- Linux distributions such as [BackTrack](http://www.backtrack-linux.org/)

## Honeypot

A honeypot is a mocked website or web application that is exposed for
attackers who try to penetrate some system. A honeypot is a non-patched server
that is monitored for attacks and serves no other purpose.

Examples include fake web servers, e-mail servers, etc.
An example of an anti-spammer project is
[Project Honey Pot](https://www.projecthoneypot.org/about_us.php).

## NIDS

NIDS (Network Intrusion Detection System) is a system that scans network
activity and tries to detect attack pattens.
There a different methods for detecting attacks:

- signature-based detection
  (example: SQL injection in an HTTP parameter)
- statistical anomaly-based detection
  (example: stealth port scan or brute force attack)
- stateful protocol analysis
  (example: detection of a SYN port scan)

An example of a NIDS is [Snort](http://www.snort.org/). It is also an IPS
(Intrusion Prevention System): Snort can take some action when it detects an
attack.

## Post-mortem analysis

After a successful attack, we need to find out *what* was compromised and
*how* the attacker succeeded to be able to fix the problem and prevent future
attacks. After an attack, we obviously need to know what parts of the system
were impacted, were they modified, was data stolen, etc.

To be able to perform a post-mortem analysis, we need to look at the traffic
statistics and logs generated by applications. Without logs we may even not
notice that an attack happened.

[Snare](http://www.intersectalliance.com) is a collection of tools to audit
log data and facilitate log analysis.
An open-source alternative is [OSSEC](http://www.ossec.net/); it performs log
analysis, file integrity checking, policy monitoring, rootkit detection,
real-time alerting and active response.
Web log analysis software allows to compute statistics and KPI's from logs
generated by web servers: [Webalizer](http://www.webalizer.org/),
[AWStats](http://awstats.sourceforge.net/),
[WebLog Expert](http://www.weblogexpert.com/), etc.

## Noise as a diversion

When an attack happens, it can be masked by another attack which would serve
as a diversion from the real attack.
Attacks that generate a lot of traffic can mask attacks because the logs
generated are mixed and an attack such as DoS could generate a lot of logs.

## Load balancing

Load balancers are able to detect whether servers are down (see failover).
They also come with tools that show the server utilization (bandwidth, CPU,
etc.).

## Performances

Performances of a web application should be checked.
Slow response time might indicate high load and that the server is currently
under attack.
If an application in development responds slowly, it might not scale and
eventually crash under high load.

References
==========

- [Police fédérale](http://www.polfed-fedpol.be/)
- Chronique RTBF

- [XKCD: Security](http://xkcd.com/538/)
- [XKCD: Password Strength](http://xkcd.com/936/)
- [XKCD: Password Reuse](http://xkcd.com/792/)

