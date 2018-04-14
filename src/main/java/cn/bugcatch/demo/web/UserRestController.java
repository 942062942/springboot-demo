package cn.bugcatch.demo.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/users")
public class UserRestController {
	//@RequestMapping(value="/{user}", method=RequestMethod.GET)
	//public User getUser(@PathVariable Long user) {
	// ...
	//}
	//@RequestMapping(value="/{user}/customers", method=RequestMethod.GET)
	//List<Customer> getUserCustomers(@PathVariable Long user) {
	// ...
	//}
	//@RequestMapping(value="/{user}", method=RequestMethod.DELETE)
	//public User deleteUser(@PathVariable Long user) {
	// ...
	//}
}
