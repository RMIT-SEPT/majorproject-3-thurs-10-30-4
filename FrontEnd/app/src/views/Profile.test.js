import React from "react";
import Profile from "./Profile";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<Profile /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <Profile /> component", ()=>{
        const component = shallow(<Profile/>);
        expect(component).toHaveLength(1);
    });
})