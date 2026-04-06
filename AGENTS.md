# About

This repository contains teaching examples for the [Middleware course](http://d3s.mff.cuni.cz/teaching/middleware).
The purpose of the examples is to demonstrate basic usage and behavior of particular middleware
technology as described in the `README.md` files. Simplicity is preferred to common production
code quality, however, deviation from reasonable practice should be noted where appropriate.


# Repository Structure

Each top level directory contains one example. The name of the top level
example directory is composed from the middleware technology used and
from the example theme.

Unless noted in a `README.md` in the top level example directory,
the next level directories contain implementations in specific
programming languages given as the directory names, such as
`c`, `java` or `python`. Each of these contains its own
`README.md` with further instructions.

    - C examples use make for build and pkgconfig for dependencies.
    - Java examples use maven for build and dependencies.
    - Python examples use uv for build and dependencies.


# General Principles

1. Treat `README.md` as the example contract
    - Each example should do what its local `README.md` says.
    - Update `README.md` if changes to code alter behavior.

2. Aim for transparent behavior
    - Use the simplest code that demonstrates the intended concepts.
    - Avoid code that distracts from the intended concepts.

3. Aim for simple execution
    - Assume local execution with simple instructions.
    - Keep the examples easy to build and run.

# Code Style

1. General
    - Adhere to general conventions of the existing examples.
    - Use typical style and idioms of the target programming language.
    - Similar example types should have similar style, structure and behavior.
    - Do not force uniformity when a technology naturally uses a different structure.

2. Naming
    - Use descriptive names from the example domain or the middleware technology.

3. Comments
    - Avoid comments where the syntax itself is obvious.
    - Include comments that point out interaction with the middleware technology.

4. Structure
    - Avoid non transparent helper code that hides middleware interactions.
    - Prefer small scale code duplication to abstraction for readability.

5. Output
    - Include output that helps observe behavior.

6. Errors
    - Include enough error handling to keep the example diagnosable.
    - Limit error handling to explicit failure reporting.

7. Dependencies
    - Keep dependencies up to date.
    - Avoid hard to maintain dependencies.
    - Keep dependencies to reasonable minimum.

8. Configuration
    - Keep configuration simple and visible.
    - Prefer defaults where they work.
    - Avoid over parametrization.
