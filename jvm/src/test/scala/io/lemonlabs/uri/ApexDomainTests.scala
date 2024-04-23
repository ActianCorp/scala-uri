package io.lemonlabs.uri

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ApexDomainTests extends AnyFlatSpec with Matchers {
  "IPv4" should "not return apex domain" in {
    Url.parse("http://1.2.3.4").apexDomain should equal(None)
  }

  "IPv6" should "not return apex domain" in {
    Url.parse("http://[::1]").apexDomain should equal(None)
  }

  "DomainName" should "return apex domain" in {
    Url.parse("http://maps.google.com").apexDomain should equal(Some("google.com"))
  }

  "DomainName" should "return apex domain for domain name in punycode" in {
    DomainName.parse("www.example.xn--6frz82g").apexDomain should equal(Some("example.xn--6frz82g"))
  }

  "DomainName" should "return apex domain for domain name in unicode" in {
    DomainName.parse("www.example.移动").apexDomain should equal(Some("example.移动"))
  }

  "DomainName" should "return apex domain for fully qualified domain name" in {
    DomainName.parse("www.hello.example.com.").apexDomain should equal(Some("example.com."))
  }

  it should "return itself for apexDomain when it is the apex domain" in {
    Url.parse("http://google.com").apexDomain should equal(Some("google.com"))
  }

  it should "not return a apexDomain when there is no known public suffix" in {
    Url.parse("http://google.blahblahblah").apexDomain should equal(None)
  }
}
