/*
Slippymap tiles driver for maps from Statens Kartverk
http://www.statkart.no/nor/Land/Kart_og_produkter/Visningstjenester/

Based on http://maps.stamen.com/js/tile.stamen.js
Available from https://gist.github.com/2295365
<script src="https://raw.github.com/gist/2295365/tile.statkart.js"></script>

FIXME:
Use Layer.XYZ instead of Layer.OSM
Add Bing http://web.archive.org/web/20091115072945/http://www.statkart.no/?module=Articles;action=Article.publicShow;ID=14167
*/
(function() {
var base="http://opencache.statkart.no/gatekeeper/gk/gk.open_gmaps"
var SUBDOMAINS = [""],
    PROVIDERS =  {
        "Kartdata": {
            "url": base+"?layers=kartdata2&zoom={Z}&x={X}&y={Y}",
            "minZoom": 0,
            "maxZoom": 16
        },
        "Sjøkart": {
            "url": base+"?layers=sjo_hovedkart2&zoom={Z}&x={X}&y={Y}",
            "minZoom": 0,
            "maxZoom": 16
        },
        "Topografisk norgeskart": {
            "url": base+"?layers=topo2&zoom={Z}&x={X}&y={Y}",
            "minZoom": 0,
            "maxZoom": 21
        },
        "Topografisk norgeskart gråtone": {
            "url": base+"?layers=topo2graatone&zoom={Z}&x={X}&y={Y}",
            "minZoom": 0,
            "maxZoom": 21
        },
        "Topografisk rasterkart": {
            "url": base+"?layers=toporaster2&zoom={Z}&x={X}&y={Y}",
            "minZoom": 0,
            "maxZoom": 16
        },
        "Europakart": {
            "url": base+"?layers=europa&zoom={Z}&x={X}&y={Y}",
            "minZoom": 0,
            "maxZoom": 17
        }
    },
    ATTRIBUTION = 'Kartgrunnlag: ' +
      '<a href="http://www.statkart.no/">Kartverket</a>, ' +
      '<a href="http://www.statkart.no/nor/Land/Fagomrader/Geovekst/">Geovekst</a> og ' +
      '<a href="http://www.statkart.no/?module=Articles;action=Article.publicShow;ID=14194">kommuner</a>';

function getProvider(name) {
    if (name in PROVIDERS) {
        return PROVIDERS[name];
    } else {
        throw 'No such provider: "' + name + '"';
    }
}

if (typeof MM === "object") {
    MM.StatkartTileLayer = function(name) {
        var provider = getProvider(name);
        MM.Layer.call(this, new MM.TemplatedMapProvider(provider.url, SUBDOMAINS));
        this.provider.setZoomRange(provider.minZoom, provider.maxZoom);
        this.attribution = ATTRIBUTION;
    };
    MM.extend(MM.StatkartTileLayer, MM.Layer);
}

if (typeof L === "object") {
    L.StatkartTileLayer = L.TileLayer.extend({
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
    OpenLayers.Layer.Statkart = OpenLayers.Class(OpenLayers.Layer.OSM, {
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
            name = options.name ? options.name : name;
            return OpenLayers.Layer.OSM.prototype.initialize.call(this, name, hosts, options);
        }
    });
}

if (typeof google === "object" && typeof google.maps === "object") {
    google.maps.StatkartMapType = function(name) {
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
    google.maps.StatkartMapType.prototype = new google.maps.ImageMapType("_");
}

})();
