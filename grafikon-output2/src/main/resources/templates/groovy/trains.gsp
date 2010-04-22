<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>${train_timetable}</title>
  <style type="text/css" media="all">
    table.index {font-family: arial, sans-serif; font-size: 3mm; border-color: black; border-style: solid; border-width: 0.7mm ;}
    table.index tr {height: 4mm;}
    table.index tr td {width: 21mm; text-align: center;}

    td.column-1 {border-color: black; border-style: solid; border-width: 0mm 0.2mm 0mm 0mm;}
    td.column-2 {border-color: black; border-style: solid; border-width: 0mm 0.7mm 0mm 0mm;}
    td.column-1-delim {border-color: black; border-style: solid; border-width: 0.2mm 0.2mm 0.4mm 0mm;}
    td.column-2-delim {border-color: black; border-style: solid; border-width: 0.2mm 0.7mm 0.4mm 0mm;}
    td.column-3-delim {border-color: black; border-style: solid; border-width: 0.2mm 0mm 0.4mm 0mm;}

    table.two-pages {page-break-after: always; width: 274mm; font-size: 3mm; font-family: arial, sans-serif; border-width: 0mm;}
    table.tt {margin: 0mm; padding: 0mm; font-family: arial, sans-serif; font-size: 3mm; width: 125mm; border-color: black; border-style: solid; border-width: 0mm; }
    table.tt tr td {border-color: black; border-style: solid; border-width: 0mm; padding: 0.3mm;}
    table.tt tr.hline {height: 4mm; text-align: center;}
    table.tt tr.line {height: 5mm;}
    table.tt tr.fline {height: 4.5mm; font-size: 3.5mm;}
    table.tt tr.fline td.totalt {border-width: 0.4mm 0.2mm 0.4mm 0mm; text-align: right;}
    table.tt tr.fline td.totali {border-width: 0.4mm 0.2mm 0.4mm 0mm; text-align: center;}
    table.tt tr.fline td.totalv {border-width: 0.4mm 0mm 0.4mm 0mm;}
    table.tt tr.line td {font-size: 3.5mm;}
    div.symbol {width: 7mm; float: left;}

    table.wl {}
    table.wl tr {height: 4mm;}
    table.wl tr td {border-width: 0; margin: 0; padding: 0; font-size: 3mm;}

    table.tt tr td.tc-d3-1 { width: 39mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-d3-2 { width: 7mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-d3-2a { width: 4mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-d3-3 { width: 7mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-d3-4 { width: 13mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-d3-5 { width: 7mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-d3-6 { width: 13mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-d3-7 { width: 7mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-d3-8 { width: 7mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-d3-9 { width: 20mm; }

    table.tt tr td.tc-1 { width: 39mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-2 { width: 8mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-3 { width: 8mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-4 { width: 16mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-5 { width: 8mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-6 { width: 16mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-7 { width: 8mm; border-right-width: 0.2mm; }
    table.tt tr td.tc-8 { width: 21mm; }

    table.tt tr td.tc-m-1 {font-size: 3.5mm; }
    table.tt tr td.tc-m-2 { text-align: center; font-size: 3.5mm; }
    table.tt tr td.tc-m-2a { text-align: center; font-size: 3.5mm; }
    table.tt tr td.tc-m-3 { text-align: center; font-size: 3.5mm; font-weight: bold;}
    table.tt tr td.tc-m-4 { text-align: right; font-size: 4mm; font-weight: bold;}
    table.tt tr td.tc-m-5 { text-align: center; font-size: 3.5mm; }
    table.tt tr td.tc-m-6 { text-align: right; font-size: 4mm; font-weight: bold;}
    table.tt tr td.tc-m-7 { text-align: center; font-size: 3.5mm; font-weight: bold;}
    table.tt tr td.tc-m-8 { font-size: 3mm; padding-left: 1mm;}
    table.tt tr td.tc-m-9 { font-size: 3mm; padding-left: 1mm;}

    table.tt tr td.tc-delim-1 {border-top-width: 0.7mm; border-bottom-width: 0.4mm;}

    td.header-l-l {text-align: left; padding-left: 0mm; width: 25%;}
    td.header-l-r {text-align: right; padding-right: 12mm; width: 25%;}
    td.header-r-l {text-align: left; padding-left: 12mm; width: 25%;}
    td.header-r-r {text-align: right; padding-right: 0mm; width: 25%;}

    td.page-left {width: 50%; vertical-align: top; padding-left:0mm; padding-right: 12mm;}
    td.page-right {width: 50%; vertical-align: top; padding-right:0mm; padding-left: 12mm;}

    div.index-title {height: 6mm; font-size: 5mm; font-weight: bold; text-align: center;}
    div.spacer4 {height: 4mm;}
    div.spacer6 {height: 6mm; font-size: 5mm;}

    tr.train-name {height: 6mm; text-align: center;}
    td.train-name {font-size: 5mm; font-weight: bold;}
    span.train-route {font-size: 3mm; font-weight: normal;}
    span.route-emph {font-weight: bold;}

    .emph {font-weight: bold;}
    img.control {height: 2.5mm; vertical-align: baseline;}
    img.signal {height: 3.5mm;}
    img.trapezoid {height: 3.5mm; vertical-align: middle;}
  </style>
</head>
<%
  INDEX_FOOTER = 10
  INDEX_HEADER = 24
  INDEX_LINE = 4

  TIMETABLE_FOOTER = 14
  TIMETABLE_HEADER = 13
  TIMETABLE_HEADER_ROUTE = 4
  TIMETABLE_HEADER_WEIGHT = 4
  TIMETABLE_LINE = 5
  TIMETABLE_COMMENT = 5

  PAGE_LENGTH = 185
%>
<body>
<%
  trainPages = createTrainPages(trains.trainTimetables)
  pages = addIndexPages(trainPages)
  printPages(pages)
%>
</body>
</html><%
  class Page {
    def number = 0
    def index = false
    def empty = false
    def trains = []

    static def emptyPage() {
      def aPage = new Page()
      aPage.empty = true
      return aPage
    }
  }

  // compute height of a timetable
  def computeTimetableHeight(train) {
    def length = 0
    length += TIMETABLE_HEADER + TIMETABLE_FOOTER
    length += TIMETABLE_LINE * train.rows.size
    if (train.weightData != null) {
      length += TIMETABLE_HEADER_WEIGHT * train.weightData.size()
    }
    if (train.routeInfo != null && train.routeInfo.size > 0)
      length += TIMETABLE_HEADER_ROUTE
    if (train.lengthData != null)
      length += TIMETABLE_HEADER_ROUTE
    def occupied = false
    def lineEnd = false
    def shunt = false
    def comments = 0
    for (row in train.rows) {
      occupied = occupied || row.occupied
      lineEnd = lineEnd || row.lineEnd
      shunt = shunt || row.shunt
      if (row.comment != null)
        comments++
    }
    if (occupied) length += TIMETABLE_COMMENT
    if (lineEnd) length += TIMETABLE_COMMENT
    if (shunt) length += TIMETABLE_COMMENT
    length += TIMETABLE_COMMENT * comments
    return length
  }

  // add index pages
  def addIndexPages(trainPages) {
    def maxCount = ((PAGE_LENGTH - INDEX_HEADER - INDEX_FOOTER).intdiv(INDEX_LINE)) * 3
    def pages = []
    def indexPage
    def i = 1
    for (page in trainPages) {
      for (train in page.trains) {
        if (indexPage == null || i > maxCount) {
          indexPage = new Page()
          indexPage.index = true
          pages << indexPage
          i = 1
        }
        indexPage.trains << [train, page]
        i++
      }
    }
    pages.addAll(trainPages)
    return pages
  }

  // create train pages
  def createTrainPages(trains) {
    // just add three trains per page
    def page = null
    def pages = []
    def pLength = 0
    for (train in trains) {
      def tLength = computeTimetableHeight(train)

      if ((pLength + tLength + 10) > PAGE_LENGTH || page == null) {
        page = new Page()
        pages << page
        pLength = tLength
      } else
        pLength += tLength
      page.trains << train
    }
    return pages
  }

  // reorder pages
  def reorderPages(pages) {
    def result = []
    int left = pages.size
    int right = 1

    while (right < (pages.size / 2)) {
        result.add(pages[left-1])
        result.add(pages[right-1])
        left -= 2
        right +=2
    }

    left = pages.size / 2
    right = (pages.size / 2) + 1

    while (right < pages.size) {
        result.add(pages[left-1])
        result.add(pages[right-1])
        left -= 2
        right += 2
    }
    return result
  }

  // number pages
  def numberPages(pages) {
    def i = 1
    for (page in pages) {
      page.number = i++
    }
  }

  // add empty pages - the count has to be divisible by 4
  def addEmptyPages(pages) {
    def added = pages.size % 4
    if (added != 0) {
      for (i in 1..(4 - added))
        pages.add(Page.emptyPage())
    }
  }

  // print all pages
  def printPages(pages) {
    addEmptyPages(pages)
    numberPages(pages)
    pages = reorderPages(pages)
    def i = pages.iterator()
    while (i.hasNext()) {
      def left = i.next()
      def right = i.next()
      printTwoPages(left, right)
    }
  }

  // print two A5 pages on one A4
  def printTwoPages(pageLeft, pageRight) {
    %>
<table class="two-pages" cellspacing="0" cellpadding="0">
<tr>
    <td class="header-l-l">${pageLeft.number}</td>
    <td class="header-l-r">OstraMo</td>
    <td class="header-r-l">OstraMo</td>
    <td class="header-r-r">${pageRight.number}</td>
</tr>
<tr>
<td colspan="2" class="page-left">
<% printPage(pageLeft) %>
</td>
<td colspan="2" class="page-right">
<% printPage(pageRight) %>
</td>
</tr>
</table><%
  }

  // print page
  def printPage(page) {
    if (page.empty == true) {
      // do nothing
    } else if (page.index == true) {
%>
<div class="index-title">${train_list}</div>
<div class="spacer4">&nbsp;</div>
<table class="index" border="0" cellspacing="0">
  <tr>
    <td class="column-1">${index_train}</td>
    <td class="column-2">${index_page}</td>
    <td class="column-1">${index_train}</td>
    <td class="column-2">${index_page}</td>
    <td class="column-1">${index_train}</td>
    <td>${index_page}</td>
  </tr>
  <tr>
    <td class="column-1-delim">1</td>
    <td class="column-2-delim">2</td>
    <td class="column-1-delim">3</td>
    <td class="column-2-delim">4</td>
    <td class="column-1-delim">5</td>
    <td class="column-3-delim">6</td>
  </tr>
  <tr>
    <td class="column-1">&nbsp;</td>
    <td class="column-2">&nbsp;</td>
    <td class="column-1">&nbsp;</td>
    <td class="column-2">&nbsp;</td>
    <td class="column-1">&nbsp;</td>
    <td>&nbsp;</td>
  </tr><%
      int repeats = (PAGE_LENGTH - INDEX_HEADER - INDEX_FOOTER) / INDEX_LINE
      for (i in 0..(repeats-1)) {
        def tRow = []
        if (i >= page.trains.size)
          break;
        tRow << getTrainPageInfo(page.trains, i)
        tRow << getTrainPageInfo(page.trains, i + repeats)
        tRow << getTrainPageInfo(page.trains, i + repeats * 2)
%>
  <tr>
    <td class="column-1">${tRow[0][0]}</td>
    <td class="column-2">${tRow[0][1]}</td>
    <td class="column-1">${tRow[1][0]}</td>
    <td class="column-2">${tRow[1][1]}</td>
    <td class="column-1">${tRow[2][0]}</td>
    <td>${tRow[2][1]}</td>
  </tr><%
      }
%>
</table><%
    } else {
      def firsttt = true
      for (train in page.trains) {
        if (!firsttt) { %>
<div class="spacer6">&nbsp;</div><%
        }
        firsttt = false
        def colspan = (train.controlled == true) ? 10 : 8
        %>
<table class="tt" cellspacing="0" cellpadding="0">
  <tr class="train-name">
    <td class="train-name" colspan="${colspan}">${train.completeName}<%
      if (train.routeInfo != null && train.routeInfo.size > 0) {%><br>
      <span class="train-route"><%
        def first = true
        for (info in train.routeInfo) { %>${!first ? " &mdash; " : ""}${info.highlighted ? "<span class=\"route-emph\">" : ""}${info.part}${info.highlighted ? "</span>" : ""}<%
          first = false
        }%></span><%
      } %></td>
  </tr>
  <tr>
    <td colspan="${colspan}">
      <table cellpadding="0" cellspacing="0" class="wl"><%
        def fwt = true
        if (train.weightData != null)
        for (wr in train.weightData) { %>
        <tr>
          <td>${wr.engine != null ? ((train.diesel ? diesel_unit : engine) + " " + wr.engine + ". &nbsp;") : ""}</td>
          <td>${(wr.weight != null && (fwt || wr.engine != null)) ? norm_load + ": &nbsp;" : ""}</td>
          <td>${wr.from != null && wr.to != null ? wr.from + " - " + wr.to + " &nbsp;" : ""}</td>
          <td align="right">${wr.weight != null ? wr.weight + " " + tons : ""}</td>
        </tr><%
          fwt = false
        }
        if (train.lengthData != null) {%>
        <tr>
          <td colspan="4">${length}: ${train.lengthData.length} ${train.lengthData.lengthInAxles ? length_axles : train.lengthData.lengthUnit}</td>
        </tr><%
        } %>
      </table>
    </td>
  </tr><%
  if (train.controlled) { %>
  <tr class="hline">
    <td class="tc-d3-1 tc-delim-1">1</td>
    <td class="tc-d3-2 tc-delim-1">2</td>
    <td class="tc-d3-2 tc-delim-1">2a</td>
    <td class="tc-d3-3 tc-delim-1">3</td>
    <td class="tc-d3-4 tc-delim-1">4</td>
    <td class="tc-d3-5 tc-delim-1">5</td>
    <td class="tc-d3-6 tc-delim-1">6</td>
    <td class="tc-d3-7 tc-delim-1">7</td>
    <td class="tc-d3-8 tc-delim-1">8</td>
    <td class="tc-d3-9 tc-delim-1">9</td>
  </tr>
  <tr class="hline">
    <td class="tc-d3-1">&nbsp;</td>
    <td class="tc-d3-2">&nbsp;</td>
    <td class="tc-d3-2a">&nbsp;</td>
    <td class="tc-d3-3">&nbsp;</td>
    <td class="tc-d3-4">&nbsp;</td>
    <td class="tc-d3-5">&nbsp;</td>
    <td class="tc-d3-6">&nbsp;</td>
    <td class="tc-d3-7">&nbsp;</td>
    <td class="tc-d3-8">&nbsp;</td>
    <td class="tc-d3-9">&nbsp;</td>
  </tr><%
  } else { %>
  <tr class="hline">
    <td class="tc-1 tc-delim-1">1</td>
    <td class="tc-2 tc-delim-1">2</td>
    <td class="tc-3 tc-delim-1">3</td>
    <td class="tc-4 tc-delim-1">4</td>
    <td class="tc-5 tc-delim-1">5</td>
    <td class="tc-6 tc-delim-1">6</td>
    <td class="tc-7 tc-delim-1">7</td>
    <td class="tc-8 tc-delim-1">8</td>
  </tr>
  <tr class="hline">
    <td class="tc-1">&nbsp;</td>
    <td class="tc-2">&nbsp;</td>
    <td class="tc-3">&nbsp;</td>
    <td class="tc-4">&nbsp;</td>
    <td class="tc-5">&nbsp;</td>
    <td class="tc-6">&nbsp;</td>
    <td class="tc-7">&nbsp;</td>
    <td class="tc-8">&nbsp;</td>
  </tr><%
  }
  def rowL = train.rows.size - 1
  def cnt = 0
  def lastSpeed = null
  def fromT = new Time()
  def toT = new Time()
  def stopDur = new Duration()
  def runDur = new Duration()
  def lastTo = null
  def lastLineClass = null
  def cChar = "*"
  for (row in train.rows) {
    def emphName = (cnt == 0) || (cnt == rowL) || row.stationType == "branch.station"
    def speed = ((lastSpeed == null || lastSpeed != row.speed) && row.speed != null) ?  row.speed : "&nbsp;"
    fromT.compute(row.arrival, cnt == rowL, row.arrival != row.departure)
    toT.compute(row.departure, false, true)
    def stationName = row.station
    def desc = ""
    if (row.stationType == "stop.with.freight") stationName += " ${abbr_stop_freight}"
    if (row.stationType == "stop") stationName += " ${abbr_stop}"
    if (emphName) stationName = "<span class=\"emph\">${stationName}</span>"
    if (row.straight == false && !row.lightSignals) desc += "&#8594;"
    if (row.lightSignals) { desc += "<img src=\"signal.gif\" class=\"signal\">"; images.add("signal.gif")}
    if (train.controlled && row.trapezoidTrains != null) {
      desc += "<img src=\"trapezoid_sign.gif\" class=\"trapezoid\">"
      images.add("trapezoid_sign.gif")
    }
    if (row.lineEnd) desc += "&Delta;"
    if (row.occupied) desc += "&Omicron;"
    if (row.shunt) desc += "&loz;"
    if (row.comment != null) {desc += cChar; cChar += "*"}
    if (desc == "") desc = "&nbsp;"
    def lineClassStr = "&nbsp;"
    if ((lastLineClass == null || (lastLineClass != row.lineClass)) && row.lineClass != null)
      lineClassStr += row.lineClass
    lastLineClass = row.lineClass
    if (train.controlled) {
      def showTrack = row.track != null && !row.controlStation && row.onControlled
      def tTrains = null
      if (row.trapezoidTrains != null) {
        for (tTrain in row.trapezoidTrains) {
          if (tTrains == null) tTrains = ""
          else tTrains += ", "
          tTrains += tTrain
        }
      }
      if (tTrains == null) tTrains = "&nbsp;"
      if (row.controlStation) images.add("control_station.gif") %>
  <tr class="line">
    <td class="tc-d3-1 tc-m-1">${stationName}${row.controlStation ? " <img src=\"control_station.gif\" class=\"control\">" : ""}</td>
    <td class="tc-d3-2 tc-m-2">${desc}</td>
    <td class="tc-d3-2a tc-m-2a">${showTrack ? row.track : "&nbsp;"}</td>
    <td class="tc-d3-3 tc-m-3">${runDur.show(lastTo, row.arrival)}</td>
    <td class="tc-d3-4 tc-m-4">${fromT.out}&nbsp;</td>
    <td class="tc-d3-5 tc-m-5">${stopDur.show(row.arrival,row.departure)}</td>
    <td class="tc-d3-6 tc-m-6">${toT.out}&nbsp;</td>
    <td class="tc-d3-7 tc-m-7">${speed}</td>
    <td class="tc-d3-8 tc-m-8">${lineClassStr}</td>
    <td class="tc-d3-9 tc-m-9">${tTrains}</td>
  </tr><%
    } else { %>
  <tr class="line">
    <td class="tc-1 tc-m-1">${stationName}</td>
    <td class="tc-2 tc-m-2">${desc}</td>
    <td class="tc-3 tc-m-3">${runDur.show(lastTo, row.arrival)}</td>
    <td class="tc-4 tc-m-4">${fromT.out}&nbsp;</td>
    <td class="tc-5 tc-m-5">${stopDur.show(row.arrival,row.departure)}</td>
    <td class="tc-6 tc-m-6">${toT.out}&nbsp;</td>
    <td class="tc-7 tc-m-7">${speed}</td>
    <td class="tc-8 tc-m-8">${lineClassStr}</td>
  </tr><%
    }
    cnt++
    lastSpeed = row.speed
    lastTo = row.departure
  }
  def totalHours = (stopDur.total + runDur.total).intdiv(60)
  def totalMinutes = (stopDur.total + runDur.total) % 60
%>
  <tr class="fline">
    <td colspan="${colspan / 2 - 2}" class="totalt">${total_train_time} &nbsp;. . . &nbsp;</td>
    <td class="totali emph">${runDur.total}</td>
    <td class="totali">+</td>
    <td class="totali">${stopDur.total}</td>
    <td colspan="${colspan / 2 - 1}" class="totalv">&nbsp;= ${totalHours != 0 ? totalHours + " " : ""}${totalHours != 0 ? hours + " " : ""}${totalMinutes != 0 ? totalMinutes : ""} ${totalMinutes != 0 ? minutes : ""}</td>
  </tr><%
  comments = createComments(train)
  for (comment in comments) { %>
  <tr class="line"><td colspan="${colspan}"><div class="symbol">${comment[0]}</div><div>= ${comment[1]}</div></td></tr><%
  } %>
</table><%
      }
    }
  }

  class Time {
    def hour
    def out = "&nbsp;"

    def compute(timeStr, forceShowHour, show) {
      def parsed = parse(timeStr)
      if (parsed == null)
        out = "&nbsp;"
      else {
        def result
        if (parsed[0] != hour || forceShowHour)
          result = "${parsed[0]} ${parsed[1]}"
        else
          result = parsed[1]

        if (show)
          hour = parsed[0]
        out = show ? result : "&nbsp;"
      }
    }

    def static parse(str) {
      if (str == null)
        return null
      else
        return str.split(":")
    }
  }

  class Duration {
    def total = 0

    def show(from,to) {
      if (from == null || to ==null)
        return "&nbsp;"
      def f = Duration.parse(from)
      def t = Duration.parse(to)
      if (t < f)
        t += 24 * 60
      def dur = t - f
      total += dur
      return dur != 0 ? dur : "&nbsp;"
    }

    def static parse(time) {
      def parsed = Time.parse(time)
      return parsed != null ? parsed[0].toInteger() * 60 + parsed[1].toInteger() : 0
    }
  }

  def getTrainPageInfo(trains, index) {
    if (index < trains.size)
      return [trains[index][0].name, trains[index][1].number]
    else
      return ["&nbsp;","&nbsp;"]
  }

  def createComments(train) {
    def symbol = "*";
    def list = []
    def shunt = false
    def occupied = false
    def lineEnd = false
    for (row in train.rows) {
      if (!lineEnd && row.lineEnd) {
        list << ["&Delta;", entry_line_end]
        lineEnd = true
      }
      if (!occupied && row.occupied) {
        list << ["&Omicron;",entry_occupied]
        occupied = true
      }
      if (!shunt && row.shunt) {
        list << ["&loz;",entry_shunt]
        shunt = true
      }
      if (row.comment != null) {
        list << [symbol,row.comment]
        symbol += "*"
      }
    }
    return list
  }
%>