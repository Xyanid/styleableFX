/*
 *
 * ******************************************************************************
 *  * Copyright 2015 - 2016 Xyanid
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package de.saxsys.styleablefx.behaviors;

import javafx.event.EventTarget;
import javafx.scene.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * This class functions as a utility class to hookup a {@link BehaviorBase} to a {@link EventTarget}.
 * Internally it will use the {@link BehaviorBase#applyBehaviorTrackable(EventTarget)} and keep a list of {@link BehaviorBase}
 * which have been used so far. Also if any known {@link BehaviorBase} should be accessed outside the manager and its {@link BehaviorBase#eventTargets}
 * was cleared, then the {@link BehaviorBase} is also removed from the {@link #knownBehaviors}.
 *
 * @author Xyanid on 23.10.2015.
 */
public final class BehaviorManager {

    // region Static

    private static final BehaviorManager INSTANCE = new BehaviorManager();

    // endregion

    // region Fields

    /**
     * Map of the known behaviors and their applied nodes.
     */
    private final Set<BehaviorBase> knownBehaviors = new HashSet<>();

    // endregion

    // region Constructor

    private BehaviorManager() {
    }

    // endregion

    // region Public

    /**
     * Returns the {@link BehaviorManager#INSTANCE}.
     *
     * @return the {@link BehaviorManager#INSTANCE}
     */
    public static BehaviorManager getInstance() {
        return INSTANCE;
    }

    /**
     * Applies the provides {@link BehaviorBase} to the provided {@link EventTarget} if it is knonw to be applied that is.
     *
     * @param behavior       {@link BehaviorBase} which is to be applied
     * @param eventTarget    {@link EventTarget} to which the behavior should be applied
     * @param <TBehavior>    type of the {@link BehaviorBase}
     * @param <TEventTarget> type of the {@link EventTarget}
     *
     * @return true if the {@link BehaviorBase} was applied to the {@link Node}, otherwise false
     *
     * @throws Exception if en exception occurs during the applying of the behavior
     */
    public final <TBehavior extends BehaviorBase<TEventTarget>, TEventTarget extends EventTarget> boolean applyBehavior(final TBehavior behavior, final TEventTarget eventTarget) throws Exception {

        if (knownBehaviors.contains(behavior)) {
            return false;
        }

        behavior.applyBehaviorTrackable(eventTarget);

        knownBehaviors.add(behavior);

        return true;
    }

    /**
     * Removed the provides {@link BehaviorBase} from the provided {@link Node} if it is known to be applied that is.
     *
     * @param behavior       {@link BehaviorBase} which is to be removed
     * @param <TBehavior>    type of the {@link BehaviorBase}
     * @param <TEventTarget> type of the {@link Node}
     *
     * @return true if the {@link BehaviorBase} was removed from the {@link Node}, otherwise false
     *
     * @throws Exception if en exception occurs during the removal of the behavior
     */
    public final <TBehavior extends BehaviorBase<TEventTarget>, TEventTarget extends EventTarget> boolean removeBehavior(final TBehavior behavior) throws Exception {

        if (!knownBehaviors.contains(behavior)) {
            return false;
        }

        behavior.removeBehavior();

        knownBehaviors.remove(behavior);

        return true;
    }

    /**
     * Removes all known {@link BehaviorBase}, clearing the {@link #knownBehaviors} in the process.
     *
     * @throws Exception if en exception occurs during the removal of the behavior
     */
    public final void clearBehaviors() throws Exception {
        for (BehaviorBase behavior : knownBehaviors) {
            behavior.removeBehavior();
        }

        knownBehaviors.clear();
    }

    // endregion
}
