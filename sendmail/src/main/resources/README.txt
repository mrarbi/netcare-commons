Sendmail is a command line tool to send emails with attachments.

It's based on standard javax.mail APIs and the implementation from Oracle (com.sun.mail).

USAGE

java -jar sendmail.jar <config-file> [attachments...]

Config file is a standard java properties file with the following properties:

# to: comma separated list of email addresses
to = name1@mail.com, name2@anothermail.com
 
# from email address
from = peter.larsson@callistaenterprise.se
 
# body text
text = Any text!
 
# subject
subject = Test
 
# content-type of attachments
attachment-type = text/csv
 
# secure smtp connection (SSL)
# secure = true
 
# host to connect to
host = smtp.gmail.com
 
# debug flag, enables trace on stdout
debug = true

# user and pwd if smtp servere requires authentication, comment out if not
# user = callista.push@gmail.com
# pwd = some_password

