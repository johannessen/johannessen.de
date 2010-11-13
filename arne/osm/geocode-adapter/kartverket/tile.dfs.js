/*
Slippymap tiles driver for maps from DFS

Based on tile.statkart.js

*/
(function() {
var base="https://secais.dfs.de/static-maps/";
var tilescheme="/tiles/{Z}/{X}/{Y}.png";
var SUBDOMAINS = [""],
    PROVIDERS =  {
        "DFS ICAO": {
            "url": base+"ICAO500-2016-EUR-Reprojected_20160321"+tilescheme,
            "minZoom": 0,
            "maxZoom": 13
        },
        "DFS ICAO 2014": {
            "url": base+"ICAO500-2014-DACH-Reprojected_01"+tilescheme,
            "minZoom": 0,
            "maxZoom": 13
        },
        "DFS VFR-Planung": {
            "url": base+"vfr_20131114"+tilescheme,
            "minZoom": 0,
            "maxZoom": 11
        },
        "DFS Lower Enroute": {
            "url": base+"lower_20131114"+tilescheme,
            "minZoom": 0,
            "maxZoom": 12
        }
    },
    ATTRIBUTION = '© DFS';

function getProvider(name) {
    if (name in PROVIDERS) {
        return PROVIDERS[name];
    } else {
        throw 'No such provider: "' + name + '"';
    }
}

if (typeof MM === "object") {
    MM.DFSTileLayer = function(name) {
        var provider = getProvider(name);
        MM.Layer.call(this, new MM.TemplatedMapProvider(provider.url, SUBDOMAINS));
        this.provider.setZoomRange(provider.minZoom, provider.maxZoom);
        this.attribution = ATTRIBUTION;
    };
    MM.extend(MM.DFSTileLayer, MM.Layer);
}

if (typeof L === "object") {
    L.DFSTileLayer = L.TileLayer.extend({
        initialize: function(name) {
            var provider = getProvider(name),
                url = provider.url.toLowerCase();
            L.TileLayer.prototype.initialize.call(this, url, {
                "minZoom":      provider.minZoom,
                "maxZoom":      provider.maxZoom,
                "subdomains":   SUBDOMAINS,
                "scheme":       "xyz",
                "attribution":  ATTRIBUTION
            });
        }
    });
}

if (typeof OpenLayers === "object") {
    // make a tile URL template OpenLayers-compatible
    function openlayerize(url) {
        return url.replace(/({.})/g, function(v) {
            return "$" + v.toLowerCase();
        });
    }

    // based on http://www.bostongis.com/PrinterFriendly.aspx?content_name=using_custom_osm_tiles
    OpenLayers.Layer.DFS = OpenLayers.Class(OpenLayers.Layer.OSM, {
        initialize: function(name, options) {
            var provider = getProvider(name),
                url = provider.url,
                hosts = [];
            if (url.indexOf("{S}") > -1) {
                for (var i = 0; i < SUBDOMAINS.length; i++) {
                    hosts.push(openlayerize(url.replace("{S}", SUBDOMAINS[i])));
                }
            } else {
                hosts.push(openlayerize(url));
            }
            options = OpenLayers.Util.extend({
                "numZoomLevels":    provider.maxZoom,
                "buffer":           0,
                "transitionEffect": "resize",
                "attribution": ATTRIBUTION
            }, options);
            return OpenLayers.Layer.OSM.prototype.initialize.call(this, name, hosts, options);
        }
    });
}

if (typeof google === "object" && typeof google.maps === "object") {
    google.maps.DFSMapType = function(name) {
        var provider = getProvider(name);
        return google.maps.ImageMapType.call(this, {
            "getTileUrl": function(coord, zoom) {
                var index = (zoom + coord.x + coord.y) % SUBDOMAINS.length;
                return [
                    provider.url
                        .replace("{S}", SUBDOMAINS[index])
                        .replace("{Z}", zoom)
                        .replace("{X}", coord.x)
                        .replace("{Y}", coord.y)
                ];
            },
            "tileSize": new google.maps.Size(256, 256),
            "name":     name,
            "minZoom":  provider.minZoom,
            "maxZoom":  provider.maxZoom
        });
    };
    // FIXME: is there a better way to extend classes in Google land?
    google.maps.DFSMapType.prototype = new google.maps.ImageMapType("_");
}

})();
