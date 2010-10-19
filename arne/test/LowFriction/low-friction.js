// $Id: $
// encoding UTF-8


window.onload = function () {
	var out = document.getElementById('out');
	loadDataAndCreateGraphs(files, out);
}



function loadDataAndCreateGraphs (fileUrls, dlOut) {
	for (var j = 0; j < fileUrls.length; j++) {
		var url = fileUrls[j];
		
		loadResource(url, function (data, urlLoaded) {
			var omss = parseOmssData(data, omssFormat);
			var graph = createDataGraph(omss);
			addGraphToDom(graph, dlOut, urlLoaded, omss);
		});
	}
}



function loadResource (uri, didLoad) {
	if (! uri) { return; }
	var httpRequest = new XMLHttpRequest();
	httpRequest.onreadystatechange = function () {
		if (httpRequest.readyState == 4) {
			if (httpRequest.status == 200 || httpRequest.status == 0) {
				// the document apparently was successfully retrieved
				didLoad(httpRequest.responseText, uri);
			}
			else {
				throw 'Error accessing resource "' + uri + '" (status code: ' + httpRequest.status + ').';
			}
		}
	};
	
	httpRequest.open('GET', uri);
	httpRequest.send(null);
}



function parseOmssData (rawData, format) {
	var dataPoints = [];
	var comments = [];
	
	var crlf = String.fromCharCode(13) + String.fromCharCode(10);
	var lines = rawData.split(crlf);
	for (var i = 0; i < lines.length; i++) {
		var line = lines[i];
		
		if (line.length == 0) {
			continue;  // empty line
		}
		if (line.charAt(0) == ';') {
			comments.push({
				content: line,
				line: i
			});
			continue;
		}
		
		dataPoints.push({
			n: Number(trim( line.substring(format.nStartCol, format.nEndCol) )),
			x: Number(trim( line.substring(format.xStartCol, format.xEndCol) )),
			y: Number(trim( line.substring(format.yStartCol, format.yEndCol) )),
			content: line,
			line: i
		});
	}
	
	return {
		data: dataPoints,
		comments: comments
	}
}



function trim (string) {
	return string.replace(/^\s+|\s+$/g, '');
}



function createDataGraph (omss) {
	var verticalExaggeration = 5;
	var scale = 3 + 1/3;
	
	var data = omss.data;
	var points = '';
	var minX = Infinity;
	var maxX = -Infinity;
	for (var i = 0; i < data.length; i++) {
		var point = data[i];
		points += '' + (i * scale) + ',' + (point.x * verticalExaggeration * scale) + ' ';
		if (point.x > maxX) {
			maxX = point.x;
		}
		if (point.x < minX) {
			minX = point.x;
		}
	}
	
	omss.maxValue = maxX;
	omss.minValue = minX;
	maxX *= verticalExaggeration * scale;
	minX *= verticalExaggeration * scale;
	var height = maxX - minX;
	var width = (data.length - 1) * scale;
	var svg = 'http://www.w3.org/2000/svg';
	
	var root = document.createElementNS(svg, 'svg');
	root.setAttribute('height', '' + height + 'px');
	root.setAttribute('width', '' + width + 'px');
	var transformation = document.createElementNS(svg, 'g');
	transformation.setAttribute('transform', 'translate(0,' + maxX + ') scale(1, -1)');
	var polyline = document.createElementNS(svg, 'polyline');
	polyline.setAttribute('points', points);
	polyline.setAttribute('class', 'results');
	
	transformation.appendChild( getGraphBackgroundGroup(width, height, omss.minValue, omss.maxValue, scale, verticalExaggeration) );
	transformation.appendChild(polyline);
	root.appendChild(transformation);
	return root;
}



function getGraphBackgroundGroup (width, height, minValue, maxValue, scale, verticalExaggeration) {
	var minY = minValue * scale * verticalExaggeration;
	var maxY = maxValue * scale * verticalExaggeration;
	var svg = 'http://www.w3.org/2000/svg';
	var backgroundGroup = document.createElementNS(svg, 'g');
	
	for (var i = 0; i < 101; i += 5) {
		var x = i * scale;
		var ordinate = document.createElementNS(svg, 'polyline');
		ordinate.setAttribute('points', '' + x + ',' + minY + ' ' + x + ',' + maxY);
		ordinate.setAttribute('class', (i % 25 == 0 ? 'major' : 'minor') + ' grid');
		backgroundGroup.appendChild(ordinate);
	}
	
	for (var i = Math.floor(minValue); i <= Math.ceil(maxValue); i += 1) {
		var y = i * scale * verticalExaggeration;
		var abscissa = document.createElementNS(svg, 'polyline');
		abscissa.setAttribute('points', '0,' + y + ' ' + width + ',' + y);
		abscissa.setAttribute('class', (i % 5 == 0 ? 'major' : 'minor') + ' grid' + (i == 0 ? ' axis' : ''));
		backgroundGroup.appendChild(abscissa);
	}
	
	return backgroundGroup;
}



function addGraphToDom (graphNode, dlNode, file, omss) {
	var title = basename(file).replace(/\.DAT$/, '');
	
	var dtNode = document.createElement('dt');
	var titleNode = document.createElement('strong');
	titleNode.appendChild(document.createTextNode(title));
	dtNode.appendChild(titleNode);
	
	dtNode.appendChild(document.createTextNode(' ('));
	var fileLinkNode = document.createElement('a');
	fileLinkNode.href = file;
	fileLinkNode.appendChild(document.createTextNode('Messdatei öffnen'));
	dtNode.appendChild(fileLinkNode);
	dtNode.appendChild(document.createTextNode(')'));
	
	var ddNode = document.createElement('dd');
	ddNode.appendChild(graphNode);
	
	var torqueLimitsInfoNode = document.createElement('p');
	torqueLimitsInfoNode.innerHTML = '<var>m</var><sub><var>x</var>,min</sub> = ' + formatTorque(omss.minValue) + ' mNm; <var>m</var><sub><var>x</var>,max</sub></var> = ' + formatTorque(omss.maxValue) + ' mNm';
	ddNode.appendChild(torqueLimitsInfoNode);
	
	var comments = omss.comments;
	if (comments && comments.length >= 2) {
		var commentsNode = document.createElement('p');
		commentsNode.appendChild(document.createTextNode(comments[1].content.substring(1)));
		ddNode.appendChild(commentsNode);
	}
	
	var insertBeforeNode = findInsertionNodeUsingInsertSort(title, dlNode);
	if (insertBeforeNode) {
		dlNode.insertBefore(dtNode, insertBeforeNode);
		dlNode.insertBefore(ddNode, insertBeforeNode);
	}
	else {
		dlNode.appendChild(dtNode);
		dlNode.appendChild(ddNode);
	}
}



function basename (path) {
	return path.replace(/^.*\//g, '');
}



function formatTorque (torque, digits) {
	var digits = Math.abs(parseInt(digits));
	if (isNaN(digits)) { digits = 1; }
	var torqueCents = roundToEven(parseFloat(torque) * Math.pow(10, digits)).toString();
	torqueCents = torqueCents.replace(/-/, '−');
	var torqueInt = torqueCents.substr(0, torqueCents.length - digits);
	return torqueInt + (torqueInt.match(/[0-9]$/) ? '' : '0') + ',' + torqueCents.substr(-digits);
}



function roundToEven (number) {
	var roundDown = Math.floor(number);
	var numberInt = roundDown == number;
	var numberDoubledInt = Math.floor(number * 2) == number * 2;
	var isExactHalfNumber = ! numberInt && numberDoubledInt;
	if (! isExactHalfNumber) {
		return Math.round(number);
	}
	return (roundDown % 2 == 0) ? roundDown : Math.ceil(number);
}



function findInsertionNodeUsingInsertSort (title, listNode) {
	var nodes = listNode.getElementsByTagName('dt');
	for (var i = 0; i < nodes.length; i++) {
		var term = nodes[i].getElementsByTagName('strong')[0].innerHTML;
		if (term > title) {
			return nodes[i];
		}
	}
	return null;
}
