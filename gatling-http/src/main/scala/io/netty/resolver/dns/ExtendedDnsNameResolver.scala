/**
 * Copyright 2011-2016 GatlingCorp (http://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.netty.resolver.dns

import java.net.InetAddress
import java.util.{ List => JList }

import io.netty.bootstrap.ChannelFactory
import io.netty.channel.EventLoop
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.resolver.HostsFileEntriesResolver
import io.netty.util.concurrent.Promise

class ExtendedDnsNameResolver(eventLoop: EventLoop)
    extends DnsNameResolver(
      eventLoop,
      new ChannelFactory[DatagramChannel] {
        override def newChannel(): DatagramChannel = new NioDatagramChannel
      },
      DnsNameResolver.ANY_LOCAL_ADDR,
      DnsServerAddresses.defaultAddresses,
      NoopDnsCache.INSTANCE,
      5000,
      DnsNameResolver.DEFAULT_RESOLVE_ADDRESS_TYPES,
      true,
      3,
      false,
      4096,
      true,
      HostsFileEntriesResolver.DEFAULT
    ) {

  override def doResolve(inetHost: String, promise: Promise[InetAddress], resolveCache: DnsCache): Unit =
    super.doResolve(inetHost, promise, resolveCache)

  override def doResolveAll(inetHost: String, promise: Promise[JList[InetAddress]], resolveCache: DnsCache): Unit =
    super.doResolveAll(inetHost, promise, resolveCache)

  override def executor: EventLoop = super.executor()
}
