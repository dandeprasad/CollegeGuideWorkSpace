import React from "react";
import { Breadcrumb, BreadcrumbItem,Container, Row, Col,Card,CardTitle,CardBody,CardText,Button,
   Form, FormGroup, Label, Input, FormText } from 'reactstrap';
import '../css/home.scss';
import { connect } from "react-redux";
import {setName} from "../actions/userActions";
import HomeCarousel from '../components/HomeCarousel';
import {paramsToHeader} from "../actions/HeaderParams";

import {Helmet} from "react-helmet";
import {homeNewsActions,homeExamsActions,homeFestsActions } from "../actions/HomeActions";
import {
  BrowserRouter as Router,
  Link,
  Route // for later
} from 'react-router-dom'
//export class App extends React.Component {   commenting because below redux will take care of attacting
 class Home extends React.Component {


//     changeUsername() {
// this.props.setName("king");
//     }

componentWillMount() {

 window.scrollTo(0, 0);
  if( this.props.match.url.includes('Home'))
  {
     this.props.paramsHeader(this.props);

  }
  this.serverRequestNews();
  this.serverRequestExams();
    this.serverRequestFests();
}

  serverRequestNews (){


    var WORKSPACE_ID = "HOME_WORKSPACE";
  		var FUNCTION_ID = "GET_NEWS_ONLY";
  		var ACTION_ID = "GET_ALL_NEWS_STRING";

    var Record = {

      "FUNCTION_ID" :FUNCTION_ID,
      "WORKSPACE_ID" : WORKSPACE_ID,
      "ACTION_ID" : ACTION_ID,
      "POSITION_NO":1

    };
  var json = {
    "datatohost" : Record
  };
  var dande = 'ServerData=' + JSON.stringify(json);




   this.props.SubmitReqNews(dande);
  }
  serverRequestExams (){

    var WORKSPACE_ID = "HOME_WORKSPACE";
  		var FUNCTION_ID = "GET_EXAMS_ONLY";
  		var ACTION_ID = "GET_CLG_EXAMS_STRING";

    var Record = {

      "FUNCTION_ID" :FUNCTION_ID,
      "WORKSPACE_ID" : WORKSPACE_ID,
      "ACTION_ID" : ACTION_ID,
      "POSITION_NO":1

    };
  var json = {
    "datatohost" : Record
  };
  var dande = 'ServerData=' + JSON.stringify(json);




   this.props.SubmitReqExams(dande);
  }
  serverRequestFests (){

      var WORKSPACE_ID = "HOME_WORKSPACE";
		var FUNCTION_ID = "GET_FESTS_NOTIFICATIONS";
		var ACTION_ID = "GET_ALL_FESTNEWS_STRING";

    var Record = {

      "FUNCTION_ID" :FUNCTION_ID,
      "WORKSPACE_ID" : WORKSPACE_ID,
      "ACTION_ID" : ACTION_ID,
      "POSITION_NO":1

    };
  var json = {
    "datatohost" : Record
  };
  var dande = 'ServerData=' + JSON.stringify(json);




   this.props.SubmitReqFests(dande);
  }
    render() {

      var items=[];var items1 = [];var items2;
        var  news=[];var exams=[];var fests=[];
      if(this.props.HomeReducer.news[0]!= undefined){
    items = this.props.HomeReducer.news[0];

    items.map((item) => {
news.push(
  <Row onClick={e => this.dandehandleClick(e)}>
  <Col xs="6" sm="6" md="5" >
 <img src={item.NEWS_IMAGE} style= {{ height: '100%',width:'100%',
    position: 'absolute',
    right: '0',
    top: '0', padding: '0px 0px 10px 0px'}}/ >
  </Col>
  <Col xs="6" sm="6" md="7" style={{padding:'10px'}}>
  <Col xs="12" sm="12" md="12" style={{padding:'0px'}}>
  <h5 className='home-news-details-h5'>{item.NEWS_HEADER}</h5>
  <p className='home-news-p'>UPDATED DATE:<span style={{color:'#df4031'}}>{item.POSTED_DATE} </span></p>

  </Col>
  </Col>
</Row>
);
    });
    news.push(    <Row>
      <Col xs="12" sm="12" md="12" >
      <Button className = "submit-button" style= {{width: '100%'}}>VIEW ALL NEWS</Button>
      </Col>
      </Row>);
      }
      if(this.props.HomeReducer.exams[0]!= undefined){
    items1 = this.props.HomeReducer.exams[0];

    items1.map((item) => {
exams.push(

  <Row>

  <Col xs="6" sm="6" md="4" >
  <Link to={'/ExamDetails/'+item.EXAMID} >
  <img src={item.LOGO_IMAGE} style= {{height: '100%',width:'100%',
     position: 'absolute',
     right: '0',
     top: '0',     padding: '0px 0px 10px 0px'}}/ >
      </Link>
  </Col>
  <Col xs="6" sm="6" md="8">
  <Col xs="12" sm="12" md="12" style={{padding:'0px'}}>
  <Link to={'/ExamDetails/'+item.EXAMID} >
  <h5 className= 'home-all-details-h5'>{item.EXAM_NAME}</h5>
  <div className = 'home-exam-details'>{item.EXAM_DETAILS} </div>
  <p>UPDATED DATE:<span style={{color:'#df4031'}}>{item.EXAM_DATE} </span></p>
  </Link>
  </Col>
  </Col>

</Row>

);
    });
    exams.push(    <Row>
      <Col xs="12" sm="12" md="12" >
      <Link to={'/Exams'} >
      <Button className = "submit-button" style= {{width: '100%'}}>VIEW ALL EXAMS</Button>
      </Link>
      </Col>
      </Row>);
      }

      if(this.props.HomeReducer.fests[0]!= undefined){
    items2 = this.props.HomeReducer.fests[0];

    items2.map((item) => {
fests.push(
  <Row>

  <Col xs="6" sm="6" md="4" >
    <Link to={'/EventDetails/'+item.CLGID} >
  <img src={item.FEST_IMAGE} style= {{height: '100%',width:'100%',
     position: 'absolute',
     right: '0',
     top: '0', padding: '0px 0px 10px 0px'}}/ >
      </Link>
  </Col>
  <Col xs="6" sm="6" md="8" style={{padding:'10px'}}>
  <Col xs="12" sm="12" md="12" style={{padding:'0px'}}>
    <Link to={'/EventDetails/'+item.CLGID} >
  <h5 className='home-all-details-h5'>{item.COLLEGE_NAME}</h5>
  <div className = 'home-exam-details'>{item.CLG_FEST_INFO} </div>
  <p>FEST DATE:<span style={{color:'#df4031'}}>{item.CLG_FEST_DATE} </span></p>
    </Link>
  </Col>
  </Col>


</Row>
);
    });
  fests.push(    <Row>
    <Col xs="12" sm="12" md="12" >
    <Link to={'/Events'} >

    <Button className = "submit-button" style= {{width: '100%'}}>VIEW ALL FESTS</Button>
    </Link>
    </Col>
    </Row>);
      }


        return (
          < Row  style={{backgroundColor: '#010c29'}} >
          <Helmet>
                          <meta charSet="utf-8" />
                          <title>College Explore </title>
            <meta name="description" content="College explore -Latest information on colleges/Universities,fests(events),exams,rankings,cut offs in India " />
<meta name="keywords" content="list of colleges in India, top colleges in India, best colleges in India.Exams,Fests,Cutoffs,Placements  "/>

            <meta property="og:image" content="https://elasticbeanstalk-us-east-2-083183914236.s3.us-east-2.amazonaws.com/collegeexplore_logo.png"/>
            <meta property="og:site_name" content="Collegeexplore"/>
            <meta property="og:type" content="website"/>
            <meta property="og:url" content="http://collegeexplore.in"/>
            <meta property="og:title" content="College Explore Latest information on Colleges"/>
            <meta property="og:description" content="Latest information on colleges/Universities,fests(events),exams,rankings,cut offs in India "/>
                      </Helmet>
<Col xs="12" sm="12" md="5" >
<div className = "homecaros1" >
<HomeCarousel/>
</div >
</Col>
<Col xs="12" sm="12" md="7" >
<div className = "homecaros2" >
<Row form>


<Col xs="12" sm="12" md="12" style={{padding:'0px'}}>
<h1 style={{color: '#fefefd',display:'none'}}>INFORMATION ON COLLEGES,FESTS,EXAMS,CUTOFFS,PLACEMENTS</h1>
<h4 style={{color: '#fefefd'}}>GET LATEST INFO ON FESTS,EXAMS ETC..</h4>
</Col>
<Col xs="12" sm="12" md="12" style={{padding:'0px'}}>
<FormGroup>
  <Input className = "searchdata" type="text" name="search" id="search" placeholder="SEARCH FOR COLLEGES,EXAMS,NEWS,ARTICLES"/>
</FormGroup>
</Col>
<Col xs="12" sm="12" md="5" style={{padding:'0px'}}>
<FormGroup>
   <Input type="select" name="select" id="exampleSelect" className = "searchdata">
     <option>Select</option>
     <option>FESTS</option>
     <option>NEWS</option>
     <option>EXAMS</option>
   </Input>
 </FormGroup>
</Col>
<Col xs="0" sm="0" md="7" style={{padding:'0px'}}>

</Col>
<Col xs="0" sm="0" md="9" style={{padding:'0px'}}>

</Col>
<Col xs="12" sm="12" md="3" style={{padding:'0px'}}>
<Button className = "submit-button" style={{margin: '10px'}} >Submit</Button>
</Col>

</Row>
</div>
</Col>

<Col xs="12" sm="12" md="12" >
<Row style={{backgroundColor: '#ffffff'}}>
<Col xs="0" sm="0" md="1" >
</Col>
<Col xs="12" sm="12" md="10" >
<Row style={{backgroundColor: '#ffffff'}}>
<Col xs="12" sm="12" md="4" >
<h5 style={{padding: '5px',fontWeight: 'bold'}}>LATEST NEWS</h5>
{news}
</Col>
<Col xs="12" sm="12" md="4" >
<h5 style={{padding: '5px',fontWeight: 'bold'}}>LATEST EXAMS</h5>
{exams}
</Col>
<Col xs="12" sm="12" md="4" >
<h5 style={{padding: '5px',fontWeight: 'bold'}}>LATEST FESTS</h5>
{fests}
</Col>
</Row>
</Col>
<Col xs="0" sm="0" md="1" >
</Col>
</Row>
</Col>

<Link to="route" style={{float: 'right',padding: '10px',color:'white'}} target="_blank" onClick={(event) => {event.preventDefault(); window.open("https://www.facebook.com/LatestCollegeNews/");}} >
<h4 style={{color:'white'}} >Facebook Page:collegeexplore</h4>
</Link>



</Row>


        );
    }
}
//state.user is userReducer
//state.math is mathReducer
const mapStateToProps = (state) => {
  return {
      HomeReducer: state.HomeReducer
  };
};
const mapDispatchToProps = (dispatch) => {
    return {
       setName:(name) => {
           dispatch(setName(name));
       },
       paramsHeader:(datatosend) => {
           dispatch(paramsToHeader(datatosend));},
           SubmitReqNews:(datatosend) => {
               dispatch(homeNewsActions(datatosend));
       },
       SubmitReqExams:(datatosend) => {
           dispatch(homeExamsActions(datatosend));
   },
   SubmitReqFests:(datatosend) => {
       dispatch(homeFestsActions(datatosend));
}

    };
};
export default connect(mapStateToProps,mapDispatchToProps)(Home);
