/*
 * PreMove.java
 * Copyright 2001 (C) Bryan McRoberts <merton_monk@yahoo.com>
 * Copyright 2003 (C) Chris Ward <frugal@purplewombat.co.uk>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	   See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Created on November 28, 2003
 *
 * Current Ver: $Revision$
 *
 */
package plugin.pretokens.test;

import pcgen.cdom.base.CDOMObject;
import pcgen.core.display.CharacterDisplay;
import pcgen.core.prereq.AbstractDisplayPrereqTest;
import pcgen.core.prereq.Prerequisite;
import pcgen.core.prereq.PrerequisiteException;
import pcgen.system.LanguageBundle;

/**
 * Passes PREMOVE tags
 */
public class PreMoveTester extends AbstractDisplayPrereqTest
{

	/*
	 * (non-Javadoc)
	 * @see pcgen.core.prereq.PrerequisiteTest#passes(pcgen.core.prereq.Prerequisite, pcgen.core.PlayerCharacter)
	 */
	@Override
	public int passes(final Prerequisite prereq, final CharacterDisplay display, CDOMObject source)
		throws PrerequisiteException
	{
		int runningTotal = 0;
		int moveAmount = 0;

		if ((display != null) && display.hasMovement())
		{
			final String moveType = prereq.getKey();

			try
			{
				moveAmount = Integer.parseInt(prereq.getOperand());
			}
			catch (NumberFormatException e)
			{
				throw new PrerequisiteException(LanguageBundle
					.getFormattedString(
						"PreMove.error.bad_operand", prereq.toString())); //$NON-NLS-1$
			}

			int speed = (int) display.getMovementOfType(moveType);
			if (speed >= moveAmount)
			{
				runningTotal += speed;
			}
		}

		runningTotal = prereq.getOperator().compare(runningTotal, moveAmount);
		return countedTotal(prereq, runningTotal);
	}

	/**
	 * Get the type of prerequisite handled by this token.
	 * @return the type of prerequisite handled by this token.
	 */
    @Override
	public String kindHandled()
	{
		return "MOVE"; //$NON-NLS-1$
	}

}
