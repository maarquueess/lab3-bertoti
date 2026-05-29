package com.thehecklers.sburrestdemo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SburRestDemoApplication {

	public static void main(String[] args) {
		Database.initialize();
		SpringApplication.run(SburRestDemoApplication.class, args);
	}

}

@RestController
@RequestMapping("/coffees")
class RestApiDemoController {

	public RestApiDemoController() {
	}

	@GetMapping
	Iterable<Coffee> getCoffees() {
		List<Coffee> coffees = new ArrayList<>();
		try (Connection conn = Database.get();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT id, nome FROM cafe")) {
			while (rs.next()) {
				coffees.add(new Coffee(rs.getLong("id"), rs.getString("nome")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coffees;
	}

	@GetMapping("/{id}")
	Optional<Coffee> getCoffeeById(@PathVariable Long id) {
		try (Connection conn = Database.get();
				PreparedStatement ps = conn.prepareStatement("SELECT id, nome FROM cafe WHERE id = ?")) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return Optional.of(new Coffee(rs.getLong("id"), rs.getString("nome")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@PostMapping
	Coffee postCoffee(@RequestBody Coffee coffee) {
		try (Connection conn = Database.get();
				PreparedStatement ps = conn.prepareStatement("INSERT INTO cafe (nome) VALUES (?)",
						Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, coffee.getName());
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					coffee.setId(rs.getLong(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coffee;
	}

	@PutMapping("/{id}")
	ResponseEntity<Coffee> putCoffee(@PathVariable Long id,
			@RequestBody Coffee coffee) {
		boolean exists = false;
		try (Connection conn = Database.get();
				PreparedStatement ps = conn.prepareStatement("UPDATE cafe SET nome = ? WHERE id = ?")) {
			ps.setString(1, coffee.getName());
			ps.setLong(2, id);
			int rows = ps.executeUpdate();
			if (rows > 0) {
				exists = true;
				coffee.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (exists) {
			return new ResponseEntity<>(coffee, HttpStatus.OK);
		} else {
			// If not exists, create it (original logic implies upsert behavior, but here
			// auto-increment makes it tricky to force ID)
			// For simplicity/standard REST, if PUT on non-existing ID, we can create.
			// But with auto-increment ID, we generally ignore the ID in path for creation
			// or error out.
			// Original code: if not exists, postCoffee (add new).
			return new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED);
		}
	}

	@DeleteMapping("/{id}")
	void deleteCoffee(@PathVariable Long id) {
		try (Connection conn = Database.get();
				PreparedStatement ps = conn.prepareStatement("DELETE FROM cafe WHERE id = ?")) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

class Coffee {
	private Long id;
	private String name;

	@JsonCreator
	public Coffee(@JsonProperty("id") Long id, @JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
	}

	public Coffee(String name) {
		this(null, name);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}