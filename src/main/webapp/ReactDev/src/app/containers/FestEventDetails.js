import React from "react";
import { Breadcrumb, BreadcrumbItem,Container, Row, Col,Card,CardTitle,CardBody,CardText,Button } from 'reactstrap';
import { connect } from "react-redux";
import img from "../img/festEventBackGrnd.jpg";
import twitter from "../img/twitter.png";
import facebook from "../img/facebook.png";
import googleplus from "../img/google-plus-logo.png";
import iitmadraslogo from "../img/iitmadraslogo.png";
import {paramsToHeader} from "../actions/HeaderParams";
import festbackground1 from "../img/festbackground1.jpg";
import '../css/festeventdetails.scss';
import FestCarousel from '../components/FestEventDetails/FestCarousel';
import FestRegistration from './FestUserRegistration';
import {eventsoverview,eventscalllinks} from "../actions/EventsDetailsActions";
import FestCarousel1 from '../components/FestEventDetails/FestCarousel1';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link ,Route} from 'react-router-dom';
class EventDetails extends React.Component {


//     changeUsername() {
// this.props.setName("king");
//     }

//style={{color: '#c24a2a'}}

constructor(props) {
  super(props);
  this.state = {
   posts:[]


  }
}
componentWillMount() {

 window.scrollTo(0, 0);
  if( this.props.match.url.includes('EventDetails'))
  {
     this.props.paramsHeader(this.props);

  }
  this.serverRequest();
  this.serverRequestCalllinks ();
}


  componentDidUpdate(nextProps){

  }




  serverRequestCalllinks ( ){
    var WORKSPACE_ID = "HOME_WORKSPACE";
  	var FUNCTION_ID = "GET_FESTS_REGIS_LINKS";
  	var ACTION_ID = "GET_FESTS_REGIS_LINKS";


  var collegeid=this.props.match.params.collegeId;

  var Record = {
		"WORKSPACE_ID" : WORKSPACE_ID,
		"FUNCTION_ID" : FUNCTION_ID,
		"ACTION_ID" : ACTION_ID,
		"FEST_ID":collegeid
	};


  var json = {
    "datatohost" : Record
  };
  var dande = 'ServerData=' + JSON.stringify(json);


   this.props.SubmitReqcalllinks(dande);
  };
  serverRequest ( ){
    var WORKSPACE_ID = "HOME_WORKSPACE";
  	var FUNCTION_ID = "GET_FESTS_OVERVIEW";
  	var ACTION_ID = "GET_FESTS_OVERVIEW";


  var collegeid=this.props.match.params.collegeId;

  var Record = {
		"WORKSPACE_ID" : WORKSPACE_ID,
		"FUNCTION_ID" : FUNCTION_ID,
		"ACTION_ID" : ACTION_ID,
		"FEST_ID":collegeid
	};

  var json = {
    "datatohost" : Record
  };
  var dande = 'ServerData=' + JSON.stringify(json);


   this.props.SubmitReq(dande);
  };





    render() {
        var fest_overview ={}
        var fest_data_links = {}
      if (this.props.EventsDetailsReduce.result.length>0)
       fest_overview =   Object.values( this.props.EventsDetailsReduce.result[0])[0];
       if (this.props.EventsDetailsReduce.links.length>0)
        fest_data_links =   Object.values( this.props.EventsDetailsReduce.links[0])[0];
        return (

          <div >
<Row className="festCont">
<img src={fest_overview.banner_logos} className="img-responsive festBackImg" alt="" width="100%" height="300"/>
<Col className="festbcdiv newsbcdiv-override" xs="12" sm="12"md="12" style={{padding:'0px'}}>
  <div className="polyleft"></div>

  <div className="polyright"></div>
  <p>
</p></Col>
<div className="breadFirst container-fluid">
      <Row>


         <Col xs="9" sm="9"md="9">
                {      /*   <Breadcrumb tag="nav" listTag="div">
        <BreadcrumbItem tag="a" href="#">Home</BreadcrumbItem>
        <BreadcrumbItem tag="a" href="#/Events">Events</BreadcrumbItem>
        <BreadcrumbItem active tag="span">Shasstra</BreadcrumbItem>
      </Breadcrumb>*/
    }
      </Col>
      <Col xs="3" sm="3"md="3">
    <Link to={'/FestRegistration'}>   <Button className=' festregbutton button'  >
Create Event
</Button></Link>
      </Col>

      </Row>
  </div>
      <div className = "center">
      <Row >
          <Col xs="12" sm="12"md="12">
{fest_overview.fest_name}
      </Col>
  </Row>
  </div>

  <div className="breadEnd container-fluid">
        <Row>
            <Col xs="12" sm="12"md="6">
<div className="feststartdate">Fest Start Date: <span style={{color: '#c24a2a'}}>{fest_overview.start_date}</span></div>
        </Col>
        <Col xs="12" sm="12"md="6">
<div className="festenddate">Fest End Date: <span style={{color: '#c24a2a'}}>{fest_overview.end_date}</span></div>
    </Col>

        </Row>
        </div>

</Row>


<Row>

<Col  xs="12" sm="12"md="8" className="fest_col_color" style={{ margin: '60px 0px 10px 0px'}}>

<Row    style={{margin: '60px 0px 60px -6px'}} >
<Col  xs="12" sm="12"md="7">
<FestCarousel/>
</Col>
<Col  xs="12" sm="12"md="5" className="fest_col_parag"
    >
<p className="fest_para_style"> {fest_overview.fest_description}</p>
</Col>
</Row>

</Col>
<Col  xs="12" sm="12"md="4">
<Row className="fest_col_depart_ty">
<Col  xs="12" sm="12"md="12">
<p style={{color: '#fff'}}> FEST DEPARTMENT:<span className="fest_col_span" >{fest_overview.fest_depart}</span></p>
</Col>
<Col  xs="12" sm="12"md="12">
<p style={{color: '#fff'}}> FEST TYPE:<span className="fest_col_span">{fest_overview.fest_type}</span></p>
</Col>
<Col  xs="12" sm="12"md="12">
<p style={{color: '#fff'}}> FEST WEBSITE:<span className="fest_col_span">{fest_data_links.fest_website}</span></p>
</Col>

<Col  xs="12" sm="12"md="12">
<p style={{color: '#fff'}}> FEST CAPTION:<span className="fest_col_span">{fest_overview.fest_caption}</span></p>
</Col>
<Col  xs="12" sm="12"md="12">
<p style={{color: '#fff'}}> FEST REGISTRATION URL:<span className="fest_col_span">{fest_data_links.fest_regis_url}</span></p>
</Col>
<Col  xs="12" sm="12"md="12">
<p style={{color: '#fff'}}> FEST LINKS:<span className="fest_col_span">{fest_data_links.fest_links}</span></p>
</Col>
</Row>
<Row className="fest_acco_row">
<p className="fest_acco"> FEST ACCOMODATION</p>
<p className="fest_col_span"> </p>
{/*<label>
  <FontAwesomeIcon
    icon="envelope"
    color="#6DB65B"
    size="sm"
  />
  {' '}Username
</label>*/}
</Row>
<Row className="facetwitgoog">
<div   style={{ margin: 'auto'}}  >
<span ><img className="img-responsive facetwitgoogspn" src="/img/facebook.png" alt="facebook"/></span>
<span ><img className="img-responsive facetwitgoogspn" src="/img/twitter.png" alt="twitter"/></span>
<span ><img className="img-responsive facetwitgoogspn" src="/img/google-plus-logo.png" alt="googleplus"/></span>
</div>
</Row>
</Col>
</Row>

<Row>
<Col xs="12" sm="12"md="12"  style={{padding: '0px'}}>
<FestCarousel1/>
</Col>
</Row>
<Row className="festenddiv2">
<Col xs="12" sm="12"md="12">
<Row>


<Col xs="12" sm="12"md="6" className="festenddiv">
<div className="festbcdivend"></div>
<img src="/img/festEventBackGrnd.jpg" className="img-responsive festBackImg1" alt="" />
<div className="festenddiv1">
<Row >
<Col xs="12" sm="12"md="12">
<h2 className="festsendtext1">HOW TO REACH</h2>
</Col>
</Row>
<Row>
<Col xs="12" sm="12"md="12">
<h3 className="festsendtext">The courses of study are organized on semester programs and each semester provides for a minimum of seventy instructional days. The medium of instruction is English. Students are evaluated on a continuous basis throughout the semester. Evaluation is done by the faculty, a consequence of the autonomous status granted to the Institute. Research work is evaluated on the basis of the review thesis by peer examiners both from within the country and abroad. Ordinances in </h3>
</Col>
</Row>

</div>
</Col>

<Col xs="12" sm="12"md="6" className="festenddiv festenddivor" >
<Row   style={{ margin: '0px'}} >
<Col xs="12" sm="12"md="12" className="festenddiv3">
<Row>
<Col >
<img src="/img/iitmadraslogo.png" className="img-responsive " alt="" />
</Col>
</Row>
<Row>
<Col>
<h4>{fest_overview.fest_college_name}</h4>
</Col>
</Row>
<Row>
<Col>
<h5><span>WEBSITE:</span>{fest_overview.college_website}</h5>
</Col>
</Row>
</Col>
</Row>
<Row style={{ padding: '28px 6px 10px 6px'}}  style={{ margin: '0px'}} >
<Col>
<h5><span>EMAIL ID :</span>--</h5>
</Col>
<Col>
<h5><span>PHONE NO :</span>--</h5>
</Col>

</Row>
</Col>

</Row>
</Col>
</Row>
{/*<div className="bc2">
<header>
  <div class="header__bg1"></div>
</header>
</div>

<div className="bc1">
<header>
  <div class="header__bg"></div>
</header>
</div>*/}

</div>
        );
    }
}




const mapStateToProps = (state,ownProps) => {

    return {
        EventsDetailsReduce: state.EventsDetailsReduce

    };
};
const mapDispatchToProps = (dispatch) => {
    return {
      SubmitReq:(datatosend) => {
          dispatch(eventsoverview(datatosend));},
SubmitReqcalllinks:(datatosend) => {
    dispatch(eventscalllinks(datatosend));},
           paramsHeader:(datatosend) => {
               dispatch(paramsToHeader(datatosend));
       }

    };
};


export default connect(mapStateToProps,mapDispatchToProps)(EventDetails);
