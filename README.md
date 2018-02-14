# The Cisco Tetration Simulator

This is a Cisco Tetration Simulator.
It was created to help test code that requires Cisco Tetration, but doesn't have a convenient access to a Cisco Tetration Target.

Provided AS IS.
The code is not provided or supported by Cisco in any way, form, or fashion.
  
## The way it works
The way the simulator works, is for every request, it will read the file in the format described below, and will generate the flow information in JSON format.

The container may get started as follows:
docker-compose up -d tetration 

### The file location and format
The file is located in /home/turbonomic/config/flow_spec.txt.
One could specify the location on the local disk by using **volumes** spec from the docker-compose.
- /tmp/config:/home/turbonomic/config

In this case, the file will be located in /tmp/config.

### The file format
<pre>
source_ip -> destination_ip:destination_port, protocol, forward_bytes/minute, reverse_bytes/minute, latency
</pre>
Please note that the following qualifiers are supported for the forward_bytes and reverse_bytes:
<ul>
<li> K - KiB
<li> M - MiB
<li> G - GiB
</ul>
The qualifiers are separated from the number by a space. The number without qualifier is handled in bytes.
<br>
The supported protocols are (case sensitive):
<ul>
<li> TCP
<li> UDP
</ul>
For example:
<pre>
172.10.150.177 -> 172.10.150.181:80, TCP, 100 M, 1 M, 0
172.10.150.177 -> 172.10.150.182:80, TCP, 100 M, 1 M, 0
172.10.150.177 -> 172.10.150.183:80, TCP, 100 M, 1 M, 0
172.10.150.177 -> 172.10.150.184:80, TCP, 100 M, 1 M, 0
</pre>  