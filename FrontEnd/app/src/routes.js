/*!

=========================================================
* Argon Dashboard React - v1.1.0
=========================================================

* Product Page: https://www.creative-tim.com/product/argon-dashboard-react
* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/argon-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/

import Timeslots from "./views/Timeslots.js";
import Index from "./views/Index.js";
import ServicesDashboard from "./views/ServicesDashboard.js";
import Profile from "./views/Profile.js";
import Maps from "./views/Maps.js";
import Register from "./views/Register.js";
import Login from "./views/Login.js";
import Tables from "./views/Tables.js";
import Icons from "./views/Icons.js";
import About from "./views/About.js";
import Admin from "./views/Admin.js";
import Worker from "./views/Worker.js";

var routes = [
  {
    path: "/services_dashboard",
    name: "Services Dashboard",
    icon: "ni ni-tv-2 text-primary",
    component: ServicesDashboard,
    layout: "/admin"
  },
  {
    path: "/user-profile",
    name: "User Profile",
    icon: "ni ni-single-02 text-yellow",
    component: Profile,
    layout: "/admin"
  },
  {
    path: "/login",
    name: "Login",
    icon: "ni ni-key-25 text-info",
    component: Login,
    layout: "/auth"
  },
  {
    path: "/register",
    name: "Register",
    icon: "ni ni-circle-08 text-pink",
    component: Register,
    layout: "/auth"
  },
  {
    path: "/about-us",
    name: "About Us",
    icon: "ni ni-circle-08 text-pink",
    component: About
  },
  {
    path: "/timeslots",
    component: Timeslots,
    layout: "/admin"
  },
  {
    path: "/admin-page",
    name: "Admin Page",
    component: Admin,
    layout: "/admin"
  },
  {
    path: "/worker-profile",
    name: "Worker Profile",
    component: Worker,
    layout: "/admin"
  }
];
export default routes;
