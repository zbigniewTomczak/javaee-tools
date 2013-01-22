Java EE Tools
============
Mail session tester
---
Jboss configuration in standalone.xml for Gmail

    <mail-session jndi-name="java:jboss/mail/Default">
      <smtp-server ssl="true" outbound-socket-binding-ref="mail-smtp">
        <login name="username" password="******"/>
      </smtp-server>
    </mail-session>
    
    <outbound-socket-binding name="mail-smtp">
      <remote-destination host="smtp.gmail.com" port="465"/>
    </outbound-socket-binding>
