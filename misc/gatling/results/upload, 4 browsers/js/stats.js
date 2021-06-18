var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "40",
        "ok": "40",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "4773",
        "ok": "4773",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "38911",
        "ok": "38911",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "28828",
        "ok": "28828",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "5135",
        "ok": "5135",
        "ko": "-"
    },
    "percentiles1": {
        "total": "28701",
        "ok": "28701",
        "ko": "-"
    },
    "percentiles2": {
        "total": "31396",
        "ok": "31396",
        "ko": "-"
    },
    "percentiles3": {
        "total": "34411",
        "ok": "34411",
        "ko": "-"
    },
    "percentiles4": {
        "total": "38906",
        "ok": "38906",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 40,
    "percentage": 100
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "1",
        "ok": "1",
        "ko": "-"
    }
},
contents: {
"req_upload-76ee3": {
        type: "REQUEST",
        name: "upload",
path: "upload",
pathFormatted: "req_upload-76ee3",
stats: {
    "name": "upload",
    "numberOfRequests": {
        "total": "40",
        "ok": "40",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "4773",
        "ok": "4773",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "38911",
        "ok": "38911",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "28828",
        "ok": "28828",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "5135",
        "ok": "5135",
        "ko": "-"
    },
    "percentiles1": {
        "total": "28701",
        "ok": "28701",
        "ko": "-"
    },
    "percentiles2": {
        "total": "31396",
        "ok": "31396",
        "ko": "-"
    },
    "percentiles3": {
        "total": "34411",
        "ok": "34411",
        "ko": "-"
    },
    "percentiles4": {
        "total": "38906",
        "ok": "38906",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 40,
    "percentage": 100
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "1",
        "ok": "1",
        "ko": "-"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
