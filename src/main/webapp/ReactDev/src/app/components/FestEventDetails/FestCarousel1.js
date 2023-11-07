import React, { Component } from 'react';
import {
  Carousel,
  CarouselItem,
  CarouselControl,
  CarouselIndicators,
  CarouselCaption,Row, Col
} from 'reactstrap';
import festbackground1 from "../../img/festbackground1.jpg";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import FestCarousel0 from '../../components/FestEventDetails/FestCarousel0';
const items = [
  {
    src: '/img/festEventBackGrnd.jpg',
    altText: '',
    caption: '',
    key:1
  },
  {
    src: '/img/festEventBackGrnd.jpg',
    altText: '',
    caption: '',
    key:2
  },
  {
    src: '/img/festEventBackGrnd.jpg',
    altText: '',
    caption: '',
    key:3
  }
];

class FestCarousel1 extends Component {
  constructor(props) {
    super(props);
    this.state = { activeIndex: 0 };
    this.next = this.next.bind(this);
    this.previous = this.previous.bind(this);
    this.goToIndex = this.goToIndex.bind(this);
    this.onExiting = this.onExiting.bind(this);
    this.onExited = this.onExited.bind(this);
  }

  onExiting() {
    this.animating = true;
  }

  onExited() {
    this.animating = false;
  }

  next() {
    if (this.animating) return;
    const nextIndex = this.state.activeIndex === items.length - 1 ? 0 : this.state.activeIndex + 1;
    this.setState({ activeIndex: nextIndex });
  }

  previous() {
    if (this.animating) return;
    const nextIndex = this.state.activeIndex === 0 ? items.length - 1 : this.state.activeIndex - 1;
    this.setState({ activeIndex: nextIndex });
  }

  goToIndex(newIndex) {
    if (this.animating) return;
    this.setState({ activeIndex: newIndex });
  }

  render() {
    const { activeIndex } = this.state;

    const slides = items.map((item) => {
      return (
        <CarouselItem
          onExiting={this.onExiting}
          onExited={this.onExited}
          key={item.key}
        >
        <Row className="festCont1">
        <img src="/img/festbackground1.jpg" className="img-responsive festBackImg" alt="" width="100%" height="425"/>
        <div className="festbcdiv festbcdiv1"></div>

        <div className="festbcdiv2">
        <div className="festbcdiv3"  >
        <span >
        <label>
          <FontAwesomeIcon
            icon="chevron-left"
            color="black"
            size="lg"
          />
          {' '}
        </label>
        <label>
          <FontAwesomeIcon
            icon="chevron-left"
            color="black"
            size="lg"
          />
          {' '}
        </label>
        </span>
        <span >
        <span className="festbcdiv4"  >Junkyard Wars</span>
        <label>
          <FontAwesomeIcon
            icon="chevron-right"
            color="black"
            size="lg"
          />
          {' '}
        </label>
        <label>
          <FontAwesomeIcon
            icon="chevron-right"
            color="black"
            size="lg"
          />
          {' '}
        </label>
        </span>

        </div>


        </div>

        <div className="festeventsdiv">
        <FestCarousel0/>
        </div>
        <div className="hexagon"> </div>
        </Row>

        </CarouselItem>
      );
    });

    return (
      <Carousel
        activeIndex={activeIndex}
        next={this.next}
        previous={this.previous}
      >

        {slides}
        <CarouselControl className = "carControl"direction="prev" directionText="Previous" onClickHandler={this.previous} />
        <CarouselControl className = "carControl" direction="next" directionText="Next" onClickHandler={this.next} />
      </Carousel>
    );
  }
}

const mapStateToProps = (state,ownProps) => {

    return {
        EventsDetailsReduce: state.EventsDetailsReduce

    };
};

export default FestCarousel1;
