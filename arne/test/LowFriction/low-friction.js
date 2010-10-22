// $Id: $
// encoding UTF-8


window.onload = function () {
	var out = document.getElementById('out');
	loadDataAndCreateGraphs(files, out);
}



function loadDataAndCreateGraphs (fileUrls, dlOut) {
	var valueSpan = document.getElementById('value');
	var maxSpan = document.getElementById('max');
	if (valueSpan && maxSpan) {
		maxSpan.innerHTML = fileUrls.length;
	}
	var value = 0;
	
	for (var j = 0; j < fileUrls.length; j++) {
		var url = fileUrls[j];
		
		loadResource(url, function (data, urlLoaded) {
			var omss = parseOmssData(data, omssFormat);
			var graph = createDataGraph(omss);
			addGraphToDom(graph, dlOut, urlLoaded, omss);
			
			value += 1;
			if (valueSpan && maxSpan) {
				valueSpan.innerHTML = value;
			}
		});
	}
}



function loadResource (uri, didLoad) {
	if (! uri) { return; }
	var httpRequest = new XMLHttpRequest();
	if (window.ActiveXObject) {
		try {  // work around issue with IE not loading local files
			httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
		catch (exception) {
			// silently ignore this condition
		}
	}
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
	httpRequest.send(null);  // :FIX: Firefox < 3.5 braucht hier 'null'
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
	
	var points = '';
	var minX = Infinity;
	var maxX = -Infinity;
	var minY = Infinity;
	var maxY = -Infinity;
	for (var i = 0; i < omss.data.length; i++) {
		var point = omss.data[i];
		var pointYSvg = point.y * -1;
		points += '' + (i * scale) + ',' + (pointYSvg * verticalExaggeration * scale) + ' ';
		if (point.x > maxX) {
			maxX = point.x;
		}
		if (point.x < minX) {
			minX = point.x;
		}
		if (point.y > maxY) {
			maxY = point.y;
		}
		if (point.y < minY) {
			minY = point.y;
		}
	}
	minYPx = Math.min(-.75, minYPx);  // have some space for axis legend
	
	omss.maxValueX = maxX;
	omss.minValueX = minX;
	omss.maxValue = maxY;
	omss.minValue = minY;
	var maxYPx = maxY * verticalExaggeration * scale;
	var minYPx = minY * verticalExaggeration * scale;
	var height = Math.abs(maxYPx - minYPx);
	var width = (omss.data.length - 1) * scale;
	var svg = 'http://www.w3.org/2000/svg';
	
	var root = document.createElementNS(svg, 'svg');
	root.setAttribute('height', '' + height + 'px');
	root.setAttribute('width', '' + width + 'px');
	var transformation = document.createElementNS(svg, 'g');
	transformation.setAttribute('transform', 'translate(0,' + maxYPx + ')');
	var polyline = document.createElementNS(svg, 'polyline');
	polyline.setAttribute('points', points);
	polyline.setAttribute('class', 'results');
	
	var template = getGraphTemplate(width, height, scale, verticalExaggeration, omss)
	transformation.appendChild(template.background);
	transformation.appendChild(template.text);
	transformation.appendChild(polyline);
	root.appendChild(transformation);
	return root;
}



function getGraphTemplate (width, height, scale, verticalExaggeration, omss) {
	var minValue = omss.minValue;
	var maxValue = omss.maxValue;
	var data = omss.data;
	
	var minYPx = minValue * scale * verticalExaggeration;
	var maxYPx = maxValue * scale * verticalExaggeration;
	var svg = 'http://www.w3.org/2000/svg';
	var backgroundGroup = document.createElementNS(svg, 'g');
	var textGroup = document.createElementNS(svg, 'g');
	
	var ordinateTextNode = document.createElementNS(svg, 'text');
	ordinateTextNode.setAttribute('class', 'ordinate');
	for (var i = 0; i <= data.length; i += 5) {
		var x = i * scale;
		var isMajor = data[i].x == omss.maxValueX || data[i].x == omss.minValueX || data[i].x % 5 == 0;
		
		var ordinate = document.createElementNS(svg, 'polyline');
		ordinate.setAttribute('points', '' + x + ',' + -minYPx + ' ' + x + ',' + -maxYPx);
		ordinate.setAttribute('class', (isMajor ? 'major' : 'minor') + ' grid');
		backgroundGroup.appendChild(ordinate);
		
		if (isMajor) {
			appendNewAxisTextSpans(x, 0, ordinateTextNode, formatNumber(data[i].x, 0) + '°');
		}
	}
	textGroup.appendChild(ordinateTextNode);
	
	var abscissaTextNode = document.createElementNS(svg, 'text');
	abscissaTextNode.setAttribute('class', 'abscissa');
	for (var i = Math.floor(minValue) * -1; i >= Math.ceil(maxValue) * -1; i -= 1) {
		var y = i * scale * verticalExaggeration;
		var isMajor = i % 5 == 0;
		
		var abscissa = document.createElementNS(svg, 'polyline');
		abscissa.setAttribute('points', '0,' + y + ' ' + width + ',' + y);
		abscissa.setAttribute('class', (isMajor ? 'major' : 'minor') + ' grid' + (i == 0 ? ' axis' : ''));
		backgroundGroup.appendChild(abscissa);
		
		if (isMajor && i != Math.floor(minValue) * -1 && i != Math.ceil(maxValue) * -1) {
			appendNewAxisTextSpans(width / 2, y, abscissaTextNode, formatNumber(-i, 0) + ' mNm ');
		}
	}
	textGroup.appendChild(abscissaTextNode);
	
	
	return {
		background: backgroundGroup,
		text: textGroup
	}
}



function appendNewAxisTextSpans (x, y, axisTextNode, text) {
	var svg = 'http://www.w3.org/2000/svg';
	var axisTextSpan = document.createElementNS(svg, 'tspan');
	axisTextSpan.setAttribute('x', x);
	axisTextSpan.setAttribute('y', y);
	axisTextSpan.appendChild(document.createTextNode( text ));
	axisTextNode.appendChild(axisTextSpan);
	axisTextNode.appendChild(axisTextSpan.cloneNode(true));
	axisTextSpan.setAttribute('class', 'mask');
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
	var average = 0;
	for (var i = 0; i < omss.data.length; i++) {
		average += omss.data[i].y;
	}
	average /= omss.data.length;
	torqueLimitsInfoNode.innerHTML = '<var>m</var><sub><var>x</var>,min</sub> ≈ ' + formatNumber(omss.minValue) + ' mNm' +
			';  <var>m</var><sub><var>x</var>,max</sub> ≈ ' + formatNumber(omss.maxValue) + ' mNm' +
			';  <span style="text-decoration: overline"><var>m</var><sub><var>x</var></sub></span> ≈ ' + formatNumber(average) + ' mNm';
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



function basename (path) {
	return path.replace(/^.*\//g, '');
}



function formatNumber (number, digits) {
	var digits = Math.abs(parseInt(digits));
	if (isNaN(digits)) { digits = 1; }
	var numberCents = roundToEven(parseFloat(number) * Math.pow(10, digits)).toString();
	var numberInt = numberCents.substr(0, numberCents.length - digits).replace(/-/, '−');
	return numberInt + (numberInt.match(/[0-9]$/) ? '' : '0') + (digits ? ',' + numberCents.substr(-digits) : '');
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
