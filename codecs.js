exported codecA,codecB,codecC,codecD */

function codecA(a, b)
{
 'use strict';
 /*jshint bitwise: false*/
if (a & b) {
return a + b;
} else {
@@ -8,7 +12,8 @@ function codecA(a, b)
}
function codecB(a, b)
{
 if (a & b) {
 'use strict';
 if (a && b) {
return a + b;
} else {
return a + ',' + b;
@@ -16,9 +21,12 @@ function codecB(a, b)
}
function codecC(a, b)
{
 'use strict';
 /*jshint eqeqeq: false*/
return a == b;
}
function codecD(a, b)
{
 return a == b;
 'use strict';
 return a === b;
}
