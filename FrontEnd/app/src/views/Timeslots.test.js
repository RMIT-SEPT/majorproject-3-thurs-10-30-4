import React from "react";
import Timeslots from "./Timeslots";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<Timeslots /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <Timeslots /> component", ()=>{
        const component = shallow(<Timeslots/>);
        expect(component).toHaveLength(1);
    });
})