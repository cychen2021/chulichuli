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
        "total": "43158",
        "ok": "43158",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "58360",
        "ok": "58360",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "52182",
        "ok": "52182",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "3787",
        "ok": "3787",
        "ko": "-"
    },
    "percentiles1": {
        "total": "52151",
        "ok": "52151",
        "ko": "-"
    },
    "percentiles2": {
        "total": "55157",
        "ok": "55157",
        "ko": "-"
    },
    "percentiles3": {
        "total": "57534",
        "ok": "57534",
        "ko": "-"
    },
    "percentiles4": {
        "total": "58337",
        "ok": "58337",
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
        "total": "0.656",
        "ok": "0.656",
        "ko": "-"
    }
},
contents: {
"req_download-fd456": {
        type: "REQUEST",
        name: "download",
path: "download",
pathFormatted: "req_download-fd456",
stats: {
    "name": "download",
    "numberOfRequests": {
        "total": "40",
        "ok": "40",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "43158",
        "ok": "43158",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "58360",
        "ok": "58360",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "52182",
        "ok": "52182",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "3787",
        "ok": "3787",
        "ko": "-"
    },
    "percentiles1": {
        "total": "52151",
        "ok": "52151",
        "ko": "-"
    },
    "percentiles2": {
        "total": "55157",
        "ok": "55157",
        "ko": "-"
    },
    "percentiles3": {
        "total": "57534",
        "ok": "57534",
        "ko": "-"
    },
    "percentiles4": {
        "total": "58337",
        "ok": "58337",
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
        "total": "0.656",
        "ok": "0.656",
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
