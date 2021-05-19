#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include <mpi.h>

int main (int iArgC, char *apArgV [])
{
  int iOutput;
  int iInput;
  int iRank;

  MPI_Init (&iArgC, &apArgV);
  MPI_Comm_rank (MPI_COMM_WORLD, &iRank);
  if (iRank == 0) iInput = 1;
             else iInput = iRank;
  MPI_Scan (&iInput, &iOutput, 1, MPI_INT, MPI_PROD, MPI_COMM_WORLD);
  printf ("Process %d: Factorial %d\n", iRank, iOutput);
  MPI_Finalize (); 

  return (0);
}
