var element = null;
var i = 0;
var name = null;
var data = {};
var ans = {};
while (true) {
	name = 'card' + i;
	element = document.getElementById(name);
	if (element == null) {
		break;
	}
	var myevent = document.createEvent('MouseEvents');
	myevent.initEvent('click', false, true);
	element.dispatchEvent(myevent);
	if (data[element.style.backgroundColor] == null) {
		data[element.style.backgroundColor] = name;
	} else {
		ans[data[element.style.backgroundColor]] = name;
	}
	i = i + 1;
}
alert('Finish!!!');
for (var key in ans) {
	//open one
	var myevent = document.createEvent('MouseEvents');
	myevent.initEvent('click', false, true);
	element = document.getElementById(key);
	element.dispatchEvent(myevent);
	//open two
	myevent = document.createEvent('MouseEvents');
	myevent.initEvent('click', false, true);
	element = document.getElementById(ans[key]);
	element.dispatchEvent(myevent);
}
