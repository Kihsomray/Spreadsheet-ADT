package model.element.value;

import model.element.Element;

/**
 * An interface that allows treating multiple distinct Elements as their integer value.
 *
 * @author Matt Bauchspies mbauch72@uw.edu
 * @version 2/27/2023
 */
public interface ValueElement extends Element {

    /**
     * Getter for whatever value the valueElement contains.
     * @return An integer value representing the element.
     */
    int getValue();

}
