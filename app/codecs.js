/*jshint unused:false*/
/*global codecA*/
function codecA(a, b)
{
    'use strict';
    //jshint bitwise:false
    if (a & b) {
        return a + b;
    } else {
        return a;
    }
}
/*global codecB*/
function codecB(a, b)
{
    'use strict';
    //jshint bitwise:false
    if (a & b) {
        return a + b;
    } else {
        return a + ',' + b;
    }
}
/*global codecC*/
function codecC(a, b)
{
    'use strict';
    return a === b;
}
/*global codecD*/
function codecD(a, b)
{
    'use strict';
    return a === b;
}