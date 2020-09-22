import React from "react";
import TimeslotHeader from "./TimeslotHeader";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<TimeslotHeader /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <TimeslotHeader /> component", ()=>{
        const component = shallow(<TimeslotHeader/>);
        expect(component).toHaveLength(1);
    });
})