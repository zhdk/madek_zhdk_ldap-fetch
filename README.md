# madek_zhdk_ldap-fetch

This dumps Groups from the LDAP System at @ZHdK into a file which we then use to
import them into http://medienarchiv.zhdk.ch/. 

The whole procedure is sort of hacky (call it pragmatic if you wish) and so is
this code! 

## Usage and Remarks 

This dumper depends on a patched version of
[clj-ldap](https://github.com/zhdk/clj-ldap)!

Something is wrong with the encoding, be it because of we use the library in
a wrong way or possibly the Ldap server doesn't report the encoding properly. 

It is at any rate necessary to convert the dumped file with
`iconv -f latin1 -t utf-8 FILE`.

UPDATE: the encoding issue got more interesting: to achieve a proper
encoding I had to pipe through `json_pp`, write to a file, and then use
`iconv -f latin1 -t utf-8 FILE`.


One way to run this is something link `MADEKSVC_PASSWORD='***' lein run`. 


